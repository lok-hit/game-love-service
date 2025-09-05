package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TopLovedGameDto(
        @JsonProperty("gameName")
        String gameName,

        @JsonProperty("loveCount")
        long loveCount
) {}

