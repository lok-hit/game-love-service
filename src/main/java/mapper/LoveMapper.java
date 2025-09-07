package mapper;

import dto.LoveDto;
import entity.Love;
import org.springframework.stereotype.Component;

@Component
public class LoveMapper {

    public LoveDto toDto(Love love) {
        return new LoveDto(
                love.getId(),
                love.getPlayerUsername(),
                love.getGameName(),
                love.getLovedAt()
        );
    }

    public Love fromDto(LoveDto dto) {
        Love love = new Love();
        love.setId(dto.id());
        love.setPlayerUsername(dto.username());
        love.setGameName(dto.gameName());
        love.setLovedAt(dto.lovedAt());
        return love;
    }
}

