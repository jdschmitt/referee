-- Alter games to point to season

ALTER TABLE `pickem`.`game`
  ADD COLUMN `season_id` BIGINT(20) NOT NULL DEFAULT 1 AFTER `game_type`,
  ADD INDEX `game_season_id_fk` (`season_id` ASC);
ALTER TABLE `pickem`.`game`
  ADD CONSTRAINT `game_season_fk`
FOREIGN KEY (`season_id`)
REFERENCES `pickem`.`player` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
