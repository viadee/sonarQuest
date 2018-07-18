ALTER TABLE World
  DROP COLUMN git_server_id;

ALTER TABLE SERVER_INFO rename to Git_Server;

Alter TABLE Git_Server
  ADD column world_id BIGINT NOT NULL UNIQUE;

ALTER TABLE Git_Server
  ADD FOREIGN KEY (world_id)
REFERENCES world (id);
