package service;

import dto.CreatePlayerDto;
import dto.PlayerDto;
import dto.UpdatePlayerDto;
import entity.Player;
import exception.ResourceNotFoundException;
import exception.ValidationException;
import mapper.PlayerMapper;
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
import repository.PlayerRepository;
import repository.PlayerSpecification;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    @CacheEvict(value = { "players", "player-search", "player-pages" }, allEntries = true)
    public PlayerDto createPlayer(CreatePlayerDto dto) {
        if (playerRepository.existsByUsername(dto.username())) {
            throw new ValidationException("Player already exists");
        }

        Player player = new Player();
        player.setUsername(dto.username());

        Player saved = playerRepository.save(player);
        return playerMapper.toDto(saved);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {ValidationException.class, RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    @CacheEvict(value = { "players", "player-search", "player-pages" }, key = "#id", allEntries = true)
    public PlayerDto updatePlayer(Long id, UpdatePlayerDto dto) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        player.setUsername(dto.username());
        Player updated = playerRepository.save(player);
        return playerMapper.toDto(updated);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "players", key = "#id")
    public PlayerDto getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        return playerMapper.toDto(player);
    }

    @Transactional
    @CacheEvict(value = { "players", "player-search", "player-pages" }, key = "#id", allEntries = true)
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Player not found");
        }
        playerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "player-search", key = "#keyword")
    public List<PlayerDto> searchPlayers(String keyword) {
        Specification<Player> spec = PlayerSpecification.nameContains(keyword);
        return playerRepository.findAll(spec).stream()
                .map(playerMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "player-pages", key = "'page:' + #page + ':size:' + #size + ':sort:' + #sortBy")
    public Page<PlayerDto> getPlayersPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return playerRepository.findAll(pageable).map(playerMapper::toDto);
    }
}
