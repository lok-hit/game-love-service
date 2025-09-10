package org.lokhit.gamelove.mapper;

import org.lokhit.gamelove.dto.LoveDto;
import org.lokhit.gamelove.entity.Love;
import org.springframework.stereotype.Component;

@Component
public class LoveMapper {

    public LoveDto toDto(Love love) {
        return new LoveDto(
                love.getId(),
                love.getPlayer(),
                love.getGame(),
                love.getLovedAt()
        );
    }

    public Love fromDto(LoveDto dto) {
        Love love = new Love();
        love.setPlayer(dto.player());
        love.setGame(dto.game());
        love.setLovedAt(dto.lovedAt());
        return love;
    }
}

