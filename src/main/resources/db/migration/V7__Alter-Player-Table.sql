ALTER TABLE player MODIFY account_expired BIT(1);
ALTER TABLE player MODIFY account_locked BIT(1);
ALTER TABLE player DROP username;