-- Set the length of the story column to 10000 characters in order to allowe lengthy stories
ALTER TABLE Quest MODIFY COLUMN Story VARCHAR (10000);
