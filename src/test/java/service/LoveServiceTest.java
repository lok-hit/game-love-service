package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Love;
import org.lokhit.gamelove.exception.ValidationException;
import org.lokhit.gamelove.mapper.LoveMapper;
import org.lokhit.gamelove.repository.GameRepository;
import org.lokhit.gamelove.repository.LoveRepository;
import org.lokhit.gamelove.service.LoveService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoveServiceTest {

    @Mock
    private LoveRepository loveRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LoveMapper loveMapper;

    @InjectMocks
    private LoveService loveService;

    @Test
    void shouldLoveGameSuccessfully() {
        String username = "mateusz";
        String gameName = "Minecraft";
        Game game = new Game();
        game.setGameName(gameName);

        when(loveRepository.existsByPlayerUsernameAndGame_GameName(username, gameName)).thenReturn(false);
        when(gameRepository.findByGameName(gameName)).thenReturn(Optional.of(game));

        loveService.loveGame(username, gameName);

        verify(loveRepository).save(Mockito.any(Love.class)); // ✅
    }

    @Test
    void shouldThrowWhenGameAlreadyLoved() {
        when(loveRepository.existsByPlayerUsernameAndGame_GameName("mateusz", "Minecraft")).thenReturn(true);

        assertThrows(ValidationException.class, () -> loveService.loveGame("mateusz", "Minecraft"));
    }
}


