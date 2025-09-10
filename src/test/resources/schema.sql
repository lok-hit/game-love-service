CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE games (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    game_name VARCHAR(255) NOT NULL
);

CREATE TABLE loves (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    player_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    loved_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_love_player FOREIGN KEY (player_id) REFERENCES players(id),
    CONSTRAINT fk_love_game FOREIGN KEY (game_id) REFERENCES games(id)
);