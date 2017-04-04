-- Create new table

CREATE TABLE `season` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_reg_game_date` datetime NOT NULL,
  `first_playoff_game_date` datetime NOT NULL,
  `super_bowl_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX `season_first_reg_game_date_index` ON `season` (`first_reg_game_date`);