-- week number should be grabbed from the associated game record

ALTER TABLE pickem.pick DROP week_number;

-- Add index to week number column in games

CREATE INDEX game_week_number_index ON pickem.game (week_number);
