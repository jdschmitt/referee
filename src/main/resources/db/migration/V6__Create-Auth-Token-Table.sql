CREATE TABLE `auth_token` (
  `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `player_id` BIGINT(20) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `expiration` DATETIME NOT NULL,
  CONSTRAINT `auth_token_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX auth_token_id_uindex ON team (id);