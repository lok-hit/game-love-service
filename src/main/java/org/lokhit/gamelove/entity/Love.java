package org.lokhit.gamelove.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "loves", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"player_username", "game_name"})
})
@NoArgsConstructor
@AllArgsConstructor
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "player", required = true)
    @Column(name = "player_username", length = 100, nullable = false)
    private String playerUsername;

    @JsonProperty("lovedAt")
    private LocalDateTime lovedAt;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public LocalDateTime getLovedAt() {
        return lovedAt;
    }

    public void setLovedAt(LocalDateTime lovedAt) {
        this.lovedAt = lovedAt;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
