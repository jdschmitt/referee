-- Create new table

CREATE TABLE `season` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_reg_game_date` datetime NOT NULL,
  `first_playoff_game_date` datetime NOT NULL,
  `super_bowl_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Add first record

INSERT INTO `pickem`.`season` (first_reg_game_date, first_playoff_game_date, super_bowl_date)
VALUES ('2016-09-08 00:00:00','2016-01-05 00:00:00','2016-02-02 00:00:00');

-- Alter games to point to season

ALTER TABLE `pickem`.`game`
ADD COLUMN `season_id` BIGINT(20) NOT NULL AFTER `game_type`,
ADD INDEX `game_season_id_fk` (`season_id` ASC);

-- Update games to point to season

UPDATE `pickem`.`game` SET `season_id` = 1;

-- Add index and foreign key constraint

ALTER TABLE `pickem`.`game`
  ADD CONSTRAINT `game_season_fk`
FOREIGN KEY (`season_id`)
REFERENCES `pickem`.`player` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
