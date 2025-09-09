package org.lokhit.gamelove.service;

import org.lokhit.gamelove.dto.CreateGameDto;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.dto.UpdateGameDto;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.exception.ResourceNotFoundException;
import org.lokhit.gamelove.exception.ValidationException;
import org.lokhit.gamelove.kafka.GameEventProducer;
import org.lokhit.gamelove.mapper.GameMapper;
import org.lokhit.gamelove.repository.GameRepository;
import org.lokhit.gamelove.repository.GameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameEventProducer gameEventProducer;

    @Autowired
    public GameService(GameRepository gameRepository,
                       GameMapper gameMapper,
                       GameEventProducer gameEventProducer) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameEventProducer = gameEventProducer;
    }


    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )

    @CacheEvict(value = {"games", "game-search", "game-pages"}, allEntries = true)
    public GameDto createGame(CreateGameDto dto) {
        if (gameRepository.existsByGameName(dto.name())) {
            throw new ValidationException("Game already exists");
        }

        Game game = new Game();
        game.setGameName(dto.name());

        Game saved = gameRepository.save(game);
        gameEventProducer.sendGameEvent(saved.getGameId(), saved.getGameName(), "CREATED");

        return gameMapper.toDto(saved);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    @CacheEvict(value = {"games", "game-search", "game-pages"}, key = "#id", allEntries = true)
    public GameDto updateGame(Long id, UpdateGameDto dto) {
        Game lockedGame = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        lockedGame.setGameName(dto.name());
        Game updated = gameRepository.save(lockedGame);
        gameEventProducer.sendGameEvent(updated.getGameId(), updated.getGameName(), "UPDATED");

        return gameMapper.toDto(updated);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "games", key = "#id")
    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        return gameMapper.toDto(game);
    }

    @Transactional
    @CacheEvict(value = {"games", "game-search", "game-pages"}, key = "#id", allEntries = true)
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found");
        }
        gameRepository.deleteById(id);
        gameEventProducer.sendGameEvent(id, "", "DELETED");
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "game-search", key = "#keyword")
    public List<GameDto> searchGames(String keyword) {
        Specification<Game> spec = GameSpecification.nameContains(keyword);
        return gameRepository.findAll(spec).stream()
                .map(gameMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "game-pages", key = "'page:' + #page + ':size:' + #size + ':sort:' + #sortBy")
    public Page<GameDto> getGamesPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return gameRepository.findAll(pageable).map(gameMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "game-pages", key = "'sorted-by-loves:' + #page + ':' + #size")
    public Page<GameDto> getGamesSortedByLoveCount(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllOrderByLoveCountDesc(pageable)
                .map(gameMapper::toDto);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}




