package org.lokhit.gamelove.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Represents a player in the system")
public record PlayerDto(

        @JsonProperty("id")
        @Schema(description = "Unique identifier of the player", example = "42")
        Long id,

        @NotBlank(message = "Username must not be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("username")
        @Schema(description = "Username of the player (letters and digits only)", example = "Mateusz123")
        String username
) {}

