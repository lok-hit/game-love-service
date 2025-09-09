package org.lokhit.gamelove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Represents a game entity")
public record GameDto(
        @JsonProperty("id")
        @Schema(description = "Unique identifier of the game", example = "1")
        Long id,

        @NotBlank(message = "Game name must not be blank")
        @Size(min = 2, max = 100, message = "Game name must be between 2 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("name")
        @Schema(description = "Name of the game (letters and digits only)", example = "Minecraft")
        String gameName
) {
}

