package org.lokhit.gamelove.service;

import org.lokhit.gamelove.dto.CreateLoveDto;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.dto.LoveDto;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Love;
import org.lokhit.gamelove.exception.ResourceNotFoundException;
import org.lokhit.gamelove.exception.ValidationException;
import org.lokhit.gamelove.mapper.LoveMapper;
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
import org.lokhit.gamelove.repository.GameRepository;
import org.lokhit.gamelove.repository.LoveRepository;
import org.lokhit.gamelove.repository.LoveSpecification;
import org.lokhit.gamelove.repository.PlayerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoveService {

    private final LoveRepository loveRepository;
    private final LoveMapper loveMapper;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public LoveService(LoveRepository loveRepository,
                       LoveMapper loveMapper,
                       PlayerRepository playerRepository,
                       GameRepository gameRepository) {
        this.loveRepository = loveRepository;
        this.loveMapper = loveMapper;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    @CacheEvict(value = { "loves", "love-search", "love-pages" }, allEntries = true)
    public LoveDto createLove(CreateLoveDto dto) {
        if (!playerRepository.existsByUsername(dto.username()) ||
                !gameRepository.existsByGameName(dto.gameName())) {
            throw new ValidationException("Invalid player or game");
        }

        if (loveRepository.existsByPlayerUsernameAndGame_GameName(dto.username(), dto.gameName())) {
            throw new ValidationException("Love already exists");
        }

        Love love = new Love();
        love.setPlayerUsername(dto.username());
        love.setLovedAt(dto.lovedAt());

        Love saved = loveRepository.save(love);
        return loveMapper.toDto(saved);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    @CacheEvict(value = { "loves", "love-search", "love-pages" }, key = "#id", allEntries = true)
    public void deleteLove(Long id) {
        if (!loveRepository.existsById(id)) {
            throw new ResourceNotFoundException("Love not found");
        }
        loveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "loves", key = "#id")
    public LoveDto getLoveById(Long id) {
        Love love = loveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Love not found"));
        return loveMapper.toDto(love);
    }
    @Transactional(readOnly = true)
    @Cacheable(value = "love-search", key = "#username + ':' + #gameName")
    public List<LoveDto> searchLoves(String username, String gameName) {
        Specification<Love> spec = Specification.unrestricted();

        if (username != null && !username.isBlank()) {
            spec = spec.and(LoveSpecification.hasPlayerUsername(username));
        }

        if (gameName != null && !gameName.isBlank()) {
            spec = spec.and(LoveSpecification.hasGame(gameName));
        }

        return loveRepository.findAll(spec).stream()
                .map(loveMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "love-pages", key = "'page:' + #page + ':size:' + #size + ':sort:' + #sortBy")
    public Page<LoveDto> getLovesPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return loveRepository.findAll(pageable).map(loveMapper::toDto);
    }

    @Transactional
    public void loveGame(String username, String gameName) {
        if (loveRepository.existsByPlayerUsernameAndGame_GameName(username, gameName)) {
            throw new ValidationException("Already loved");
        }

        Game game = gameRepository.findByGameName(gameName)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        Love love = new Love();
        love.setPlayerUsername(username);
        love.setGame(game);
        love.setLovedAt(LocalDateTime.now());

        loveRepository.save(love);
    }

    @Transactional
    public void unloveGame(String username, String gameName) {
        List<Love> loves = loveRepository.findByPlayerUsername(username).stream()
                .filter(l -> l.getGame().getGameName().equalsIgnoreCase(gameName))
                .toList();

        if (loves.isEmpty()) {
            throw new ResourceNotFoundException("Love not found");
        }

        loveRepository.deleteAll(loves);
    }

    @Transactional(readOnly = true)
    public List<LoveDto> getLoveHistory(String username) {
        return loveRepository.findByPlayerUsername(username).stream()
                .map(loveMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GameDto> getTopLovedGames(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return gameRepository.findAllOrderByLoveCountDesc(pageable).stream()
                .map(game -> new GameDto(game.getGameId(), game.getGameName()))
                .toList();
    }



}

