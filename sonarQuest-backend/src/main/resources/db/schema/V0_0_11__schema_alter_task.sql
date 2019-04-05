-- Task extended by issue_rule which specifies the rule this task was created upon. May be NULL
ALTER TABLE Task ADD COLUMN issue_rule VARCHAR(256);
