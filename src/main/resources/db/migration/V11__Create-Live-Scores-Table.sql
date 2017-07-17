CREATE TABLE live_score_week
(
  game_dow VARCHAR(10) NOT NULL,
  game_time VARCHAR(5) NOT NULL,
  game_quarter VARCHAR(10) NOT NULL,
  game_quarter_time VARCHAR(10),
  away_team VARCHAR(3) NOT NULL,
  away_team_score INT UNSIGNED,
  home_team VARCHAR(3) NOT NULL,
  home_team_score INT UNSIGNED,
  possession_team VARCHAR(3),
  in_red_zone BIT,
  nfl_game_id BIGINT(20) PRIMARY KEY NOT NULL,
  unknown_value VARCHAR(100),
  season_week VARCHAR(10) NOT NULL,
  season_year INT NOT NULL
);
CREATE UNIQUE INDEX live_score_week_nfl_game_id_uindex ON live_score_week (nfl_game_id);