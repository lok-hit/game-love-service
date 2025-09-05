package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record LoveDto(
        @JsonProperty("id")
        Long id,

        @NotBlank(message = "Username must not be blank")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("username")
        String username,

        @NotBlank(message = "Game name must not be blank")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("gameName")
        String gameName,

        @NotNull(message = "Timestamp must not be null")
        @JsonProperty("lovedAt")
        LocalDateTime lovedAt
) {}

