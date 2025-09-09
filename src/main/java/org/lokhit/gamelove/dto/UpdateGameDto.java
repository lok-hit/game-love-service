package org.lokhit.gamelove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for updating an existing game")
public record UpdateGameDto(

        @Schema(description = "Updated name of the game", example = "Terraria")
        @NotBlank(message = "Game name must not be blank")
        @Size(min = 2, max = 100)
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("name")
        String name
) {}

