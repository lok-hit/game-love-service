package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GameDto(
        @JsonProperty("id")
        Long id,

        @NotBlank(message = "Game name must not be blank")
        @Size(min = 2, max = 100, message = "Game name must be between 2 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
        @JsonProperty("name")
        String name
) {}

