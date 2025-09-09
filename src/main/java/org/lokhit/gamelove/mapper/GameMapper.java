package org.lokhit.gamelove.mapper;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDto toDto(Game game) {
        return new GameDto(game.getGameId(), game.getGameName());
    }

    public Game fromDto(GameDto dto) {
        Game game = new Game();
        game.setGameName(dto.gameName());
        return game;
    }
}

