package org.lokhit.gamelove.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(namespace = "game_id")
    private Long gameId;

    @JsonProperty(value = "game_name", required = true)
    @Column(name = "game_name")
    private String gameName;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("loves")
    private Set<Love> loves = new HashSet<>();

    public Long getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public Set<Love> getLoves() {
        return loves;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setLoves(Set<Love> loves) {
        this.loves = loves;
    }
}
