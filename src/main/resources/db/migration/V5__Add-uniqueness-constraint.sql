DROP INDEX settings_season_id_fk_idx ON settings;
CREATE UNIQUE INDEX settings_season_id_fk_idx ON settings (season_id);