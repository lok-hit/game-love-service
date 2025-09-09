package org.lokhit.gamelove.mapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.lokhit.gamelove.dto.PlayerDto;
import org.lokhit.gamelove.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {


    public PlayerDto toDto(Player player) {
        return new PlayerDto(player.getId(), player.getUsername());
    }

    public Player fromDto(PlayerDto dto) {
        Player player = new Player();
        player.setUsername(dto.username());
        return player;
    }
}


