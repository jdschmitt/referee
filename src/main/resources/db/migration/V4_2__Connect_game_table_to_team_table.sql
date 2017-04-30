ALTER TABLE game MODIFY away_team INT(10) UNSIGNED NOT NULL;
ALTER TABLE game MODIFY home_team INT(10) UNSIGNED NOT NULL;

ALTER TABLE game
ADD CONSTRAINT game_away_team_id_fk
FOREIGN KEY (away_team) REFERENCES team (id);
ALTER TABLE game
ADD CONSTRAINT game_home_team_id_fk
FOREIGN KEY (home_team) REFERENCES team (id);