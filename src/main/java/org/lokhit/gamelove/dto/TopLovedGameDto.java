package org.lokhit.gamelove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Represents a game with its total love count")
public record TopLovedGameDto(
        @JsonProperty("gameName")
        @Schema(description = "Name of the game", example = "Minecraft")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        String gameName,

        @JsonProperty("loveCount")
        @Schema(description = "Number of times the game was loved", example = "128")
        long loveCount
) {}

