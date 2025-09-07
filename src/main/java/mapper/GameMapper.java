package mapper;


import dto.GameDto;
import entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDto toDto(Game game) {
        return new GameDto(game.getGameId(), game.getGameName());
    }

    public Game fromDto(GameDto dto) {
        Game game = new Game();
        game.setGameId(dto.id());
        game.setGameName(dto.name());
        return game;
    }
}

