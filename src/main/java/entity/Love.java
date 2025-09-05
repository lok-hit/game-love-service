package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;


    @ManyToOne
    @NotNull(message = "Player must not be null")
    @JsonProperty(value = "player", required = true)
    private Player player;

    @ManyToOne
    @NotNull(message = "Game must not be null")
    @JsonProperty(value = "game", required = true)
    private Game game;

    @NotNull(message = "Timestamp must not be null")
    @JsonProperty("lovedAt")
    private LocalDateTime lovedAt;

}
