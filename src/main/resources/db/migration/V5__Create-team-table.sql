-- Create new table

CREATE TABLE `team` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `abbreviation` VARCHAR(3)NOT NULL,
  `market` VARCHAR(50) NOT NULL,
  `mascot` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
