package dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for creating a new game")
public record CreateGameDto(

        @Schema(description = "Name of the game", example = "Minecraft")
        @NotBlank(message = "Game name must not be blank")
        @Size(min = 2, max = 100)
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("name")
        String name
) {}

