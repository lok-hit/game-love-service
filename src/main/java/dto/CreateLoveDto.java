package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Schema(description = "DTO for creating a love entry")
public record CreateLoveDto(

        @Schema(description = "Username of the player", example = "Mateusz123")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        @JsonProperty("username")
        String username,

        @Schema(description = "Name of the game", example = "Minecraft")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        @JsonProperty("gameName")
        String gameName,

        @Schema(description = "Timestamp of the love", example = "2025-09-05T22:00:00")
        @NotNull
        @JsonProperty("lovedAt")
        LocalDateTime lovedAt
) {}

