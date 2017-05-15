ALTER TABLE player_role
  ADD CONSTRAINT player_role_role_id_fk
FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE player_role
  ADD CONSTRAINT player_role_player_id_fk
FOREIGN KEY (role_id) REFERENCES player (id);