package mapper;

import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;
import org.lokhit.gamelove.dto.LoveDto;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Love;
import org.lokhit.gamelove.entity.Player;
import org.lokhit.gamelove.mapper.LoveMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoveMapperTest {

    private final LoveMapper mapper = new LoveMapper();
    private final Player player = new Player();
    private Game game = new Game();

    @Before
    void setup(){
        player.setId(1L);
        player.setLoves(new ArrayList<>());
        player.setUsername("mateusz");
        game.setGameName("Minecraft");
        game.setLoves(new HashSet<>());
        game.setGameId(1L);
    }

    @Test
    void shouldMapLoveToDtoCorrectly() {
        // given
        game.setGameName("Minecraft");
        Love love = new Love();
        love.setId(5L);
        love.setPlayer(player);
        love.setGame(game);
        love.setLovedAt(LocalDateTime.of(2025, 9, 9, 15, 30));

        // when
        LoveDto dto = mapper.toDto(love);

        // then
        assertEquals("mateusz", dto.player().getUsername());
        assertEquals("Minecraft", dto.game().getGameName());
        assertEquals(LocalDateTime.of(2025, 9, 9, 15, 30), dto.lovedAt());
    }

    @Test
    void shouldHandleNullGameGracefully() {
        // given
        Love love = new Love();
        love.setId(5L);
        love.setPlayer(player);
        love.setGame(game);
        love.setLovedAt(LocalDateTime.of(2025, 9, 9, 15, 30));

        // when
        LoveDto dto = mapper.toDto(love);

        // then
        assertEquals("mateusz", dto.player().getUsername());
        assertNull(dto.game().getGameName());
        assertEquals(LocalDateTime.of(2025, 9, 9, 15, 30), dto.lovedAt());
    }
}