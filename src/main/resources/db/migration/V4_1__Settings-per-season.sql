
-- Changing settings to be per season

ALTER TABLE pickem.settings CHANGE current_season_id season_id INT(10) unsigned NOT NULL;
ALTER TABLE pickem.settings DROP FOREIGN KEY settings_current_season_id_fk;
DROP INDEX settings_current_season_id_fk_idx ON pickem.settings;
CREATE INDEX settings_season_id_fk_idx ON pickem.settings (season_id);
ALTER TABLE pickem.settings
  ADD CONSTRAINT settings_season_id_fk
FOREIGN KEY (season_id) REFERENCES season (id);

-- Need index in first regular season date so that we can use it to find current season to display

CREATE INDEX season_first_reg_game_date_index ON pickem.season (first_reg_game_date);