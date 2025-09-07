package mapper;

import dto.PlayerDto;
import entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDto toDto(Player player) {
        return new PlayerDto(player.getId(), player.getUsername());
    }

    public Player fromDto(PlayerDto dto) {
        Player player = new Player();
        player.setId(dto.id());
        player.setUsername(dto.username());
        return player;
    }
}


