package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity(name ="game")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(namespace = "game_id")
    private Long gameId;

    @NotBlank(message = "Name of the game cannot be blank")
    @Size(min = 2, max = 100, message = "Size should be between 2 and 100 characters")
    @JsonProperty(value = "game_name", required = true)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and digits allowed")
    private String gameName;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("love")
    private Set<Love> loves;
}
