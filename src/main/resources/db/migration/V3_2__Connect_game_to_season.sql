-- Alter games to point to season

ALTER TABLE `game`
  ADD COLUMN `season_id` INT(10) UNSIGNED NOT NULL AFTER `game_type`,
  ADD INDEX `game_season_id_fk_idx` (`season_id` ASC);

UPDATE `game` SET `season_id` = 1;

ALTER TABLE `game`
  ADD CONSTRAINT `game_season_id_fk`
FOREIGN KEY (`season_id`)
REFERENCES `season` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `settings`
  ADD COLUMN `current_season_id` INT(10) UNSIGNED NOT NULL AFTER `winners_per_week`,
  ADD INDEX `settings_current_season_id_fk_idx` (`current_season_id` ASC);

UPDATE `settings` SET `current_season_id` = 1;

ALTER TABLE `settings`
  ADD CONSTRAINT `settings_current_season_id_fk`
FOREIGN KEY (`current_season_id`)
REFERENCES `season` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;