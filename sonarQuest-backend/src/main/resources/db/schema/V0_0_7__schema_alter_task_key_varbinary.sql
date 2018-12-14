-- Set the task_key to VARBINARY to allow for case sensitive searches in MYSQL databases
ALTER TABLE Task MODIFY COLUMN task_key VARBINARY;
