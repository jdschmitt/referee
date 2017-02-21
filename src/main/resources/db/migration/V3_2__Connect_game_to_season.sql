-- Alter games to point to season

ALTER TABLE `game`
  ADD COLUMN `season_id` BIGINT(20) NOT NULL AFTER `game_type`,
  ADD INDEX `game_season_id_fk` (`season_id` ASC);

UPDATE `game` SET `season_id` = 1;

ALTER TABLE `game`
  ADD CONSTRAINT `game_season_fk`
FOREIGN KEY (`season_id`)
REFERENCES `player` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
