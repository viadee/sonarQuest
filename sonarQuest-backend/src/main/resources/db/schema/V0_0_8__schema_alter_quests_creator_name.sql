-- Quests now have the creator name saved
ALTER TABLE Quest ADD COLUMN creator_name VARCHAR(64);
