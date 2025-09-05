package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "Represents a love relationship between a player and a game")
public record LoveDto(
        @JsonProperty("id")
        @Schema(description = "Unique identifier of the love entry", example = "100")
        Long id,

        @NotBlank(message = "Username must not be blank")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("username")
        @Schema(description = "Username of the player who loved the game", example = "Mateusz123")
        String username,

        @NotBlank(message = "Game name must not be blank")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("gameName")
        @Schema(description = "Name of the game that was loved", example = "Minecraft")
        String gameName,

        @NotNull(message = "Timestamp must not be null")
        @JsonProperty("lovedAt")
        @Schema(description = "Timestamp when the love was recorded", example = "2025-09-05T22:00:00")
        LocalDateTime lovedAt
) {}

