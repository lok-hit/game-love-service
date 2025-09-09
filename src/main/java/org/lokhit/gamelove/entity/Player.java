package org.lokhit.gamelove.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "username", required = true)
    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(value = "loves", required = true)
    private Set<Love> loves;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Love> getLoves() {
        return loves;
    }

    public void setLoves(Set<Love> loves) {
        this.loves = loves;
    }
}


