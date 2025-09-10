package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lokhit.gamelove.GameLoveServiceApplication;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Love;
import org.lokhit.gamelove.entity.Player;
import org.lokhit.gamelove.repository.GameRepository;
import org.lokhit.gamelove.repository.LoveRepository;
import org.lokhit.gamelove.repository.LoveSpecification;
import org.lokhit.gamelove.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(GameLoveServiceApplication.class)
@ContextConfiguration(classes = GameLoveServiceApplication.class)
class LoveSpecificationIntegrationTest {

    @Autowired
    private LoveRepository loveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    private Player player;
    private Game game;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setUsername("mateusz");
        player = playerRepository.save(player);

        game = new Game();
        game.setGameName("Minecraft");
        game = gameRepository.save(game);

        Love love = new Love();
        love.setPlayer(player);
        love.setGame(game);
        love.setLovedAt(LocalDateTime.of(2025, 9, 9, 12, 0));
        loveRepository.save(love);
    }

    @Test
    void shouldFindByPlayerUsername() {
        Specification<Love> spec = LoveSpecification.hasPlayerUsername("mateusz");
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals("mateusz", result.get(0).getPlayer().getUsername());
    }

    @Test
    void shouldFindByGameNameIgnoreCase() {
        Specification<Love> spec = LoveSpecification.hasGame("minecraft");
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals("Minecraft", result.get(0).getGame().getGameName());
    }

    @Test
    void shouldFindByPlayerId() {
        Long playerId = loveRepository.findAll().get(0).getPlayer().getId();
        Specification<Love> spec = LoveSpecification.hasPlayerId(playerId);
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals(playerId, result.get(0).getPlayer().getId());
    }

    @Test
    void shouldFindByGameId() {
        Long gameId = loveRepository.findAll().get(0).getGame().getGameId();
        Specification<Love> spec = LoveSpecification.hasGameId(gameId);
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals(gameId, result.get(0).getGame().getGameId());
    }

    @Test
    void shouldFindLovedAfter() {
        Specification<Love> spec = LoveSpecification.lovedAfter(LocalDateTime.of(2025, 9, 8, 0, 0));
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindLovedBefore() {
        Specification<Love> spec = LoveSpecification.lovedBefore(LocalDateTime.of(2025, 9, 10, 0, 0));
        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
    }

    @Test
    void shouldCombineMultipleSpecifications() {
        Specification<Love> spec = LoveSpecification.hasPlayerUsername("mateusz")
                .and(LoveSpecification.hasGame("Minecraft"))
                .and(LoveSpecification.lovedAfter(LocalDateTime.of(2025, 9, 1, 0, 0)));

        List<Love> result = loveRepository.findAll(spec);

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEmptyWhenNoMatch() {
        Specification<Love> spec = LoveSpecification.hasPlayerUsername("nonexistent")
                .and(LoveSpecification.hasGame("Unknown"));

        List<Love> result = loveRepository.findAll(spec);

        assertTrue(result.isEmpty());
    }
}

