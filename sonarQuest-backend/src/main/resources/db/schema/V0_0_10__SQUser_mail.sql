-- SQUser extended by mail attribute
ALTER TABLE SQUser ADD COLUMN mail VARCHAR(255)  UNIQUE;
