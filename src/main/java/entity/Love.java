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
@Table(name = "loves", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "player_username", "game_name" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Player must not be null")
    @JsonProperty(value = "player", required = true)
    @Column(name = "player_username", length = 100, nullable = false)
    private String playerUsername;

    @NotNull(message = "Game must not be null")
    @JsonProperty(value = "game", required = true)
    @Column(name="game_name", length = 100, nullable = false)
    private String gameName;

    @NotNull(message = "Timestamp must not be null")
    @JsonProperty("lovedAt")
    private LocalDateTime lovedAt;

}
