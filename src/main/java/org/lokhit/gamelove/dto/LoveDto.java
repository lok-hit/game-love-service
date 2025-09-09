package org.lokhit.gamelove.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


@Schema(description = "DTO representing a player's love for a game")
public record LoveDto(

        long id,
        @Schema(description = "Username of the player who liked the game", example = "mateusz", required = true)
        String playerUsername,

        @Schema(description = "Name of the liked game", example = "The Witcher 3", required = true)
        @NotBlank(message = "Game name cannot be blank")
        @Size(min = 2, max = 150, message = "Game name must be between 2 and 150 characters")
        String gameName,

        @Schema(description = "Timestamp when the game was liked", example = "2025-09-09T03:02:00")
        LocalDateTime lovedAt
) {}



