package service;

import dto.CreateLoveDto;
import dto.LoveDto;
import entity.Love;
import exception.ResourceNotFoundException;
import exception.ValidationException;
import mapper.LoveMapper;
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
import repository.GameRepository;
import repository.LoveRepository;
import repository.LoveSpecification;
import repository.PlayerRepository;

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
                !gameRepository.existsByName(dto.gameName())) {
            throw new ValidationException("Invalid player or game");
        }

        if (loveRepository.existsByPlayerUsernameAndGameName(dto.username(), dto.gameName())) {
            throw new ValidationException("Love already exists");
        }

        Love love = new Love();
        love.setPlayerUsername(dto.username());
        love.setGameName(dto.gameName());
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


}

