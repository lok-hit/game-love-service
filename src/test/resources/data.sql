INSERT INTO players (id, username) VALUES (1, 'mateusz');
INSERT INTO games (id, game_name) VALUES (1, 'Minecraft');

INSERT INTO loves (id, player_id, game_id, loved_at) VALUES
  (1, 1, 1, CURRENT_TIMESTAMP);