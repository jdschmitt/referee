ALTER TABLE pick
  ADD CONSTRAINT pick_game_id_fk
FOREIGN KEY (game_id) REFERENCES game (id);
ALTER TABLE pick
  ADD CONSTRAINT pick_player_id_fk
FOREIGN KEY (player_id) REFERENCES player (id);
ALTER TABLE pick CHANGE team team_id INT(10) UNSIGNED NOT NULL;
ALTER TABLE pick
  ADD CONSTRAINT pick_team_id_fk
FOREIGN KEY (team_id) REFERENCES team (id);
ALTER TABLE pick MODIFY version BIGINT(20);
ALTER TABLE pick MODIFY is_correct BIT(1) NOT NULL DEFAULT 0;