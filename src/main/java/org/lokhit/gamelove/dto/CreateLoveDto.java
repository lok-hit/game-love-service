package org.lokhit.gamelove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.entity.Player;

import java.time.LocalDateTime;

@Schema(description = "DTO for creating a love entry")
public record CreateLoveDto(

        @Schema(description = " player")
        @NotBlank
        @JsonProperty("player")
        Player player,

        @Schema(description = "game")
        @NotNull
        Game game,

        @Schema(description = "Timestamp of the love", example = "2025-09-05T22:00:00")
        @NotNull
        @JsonProperty("lovedAt")
        LocalDateTime lovedAt
) {}

