-- week number should be grabbed from the associated game record

ALTER TABLE `pick` DROP `week_number`;

-- Add index to week number column in games

CREATE INDEX `game_week_number_index` ON `game` (`week_number`);
