package org.lokhit.gamelove.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Player;

import java.time.LocalDateTime;


@Schema(description = "DTO representing a player's love for a game")
public record LoveDto(

        long id,
        @Schema(description = "Username of the player who liked the game", example = "mateusz", required = true)
        Player player,

        @Schema(description = "Name of the liked game", example = "The Witcher 3", required = true)
        @NotNull(message = "Game name cannot be blank")
        Game game,

        @Schema(description = "Timestamp when the game was liked", example = "2025-09-09T03:02:00")
        LocalDateTime lovedAt
) {}



