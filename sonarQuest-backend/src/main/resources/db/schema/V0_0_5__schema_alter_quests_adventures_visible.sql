-- Quests may or may not be visible (nullable)
ALTER TABLE Quest ADD COLUMN visible BOOLEAN;
ALTER TABLE Adventure ADD COLUMN visible BOOLEAN;
