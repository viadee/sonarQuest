ALTER TABLE Task ADD COLUMN score DOUBLE NOT NULL DEFAULT 1;

CREATE TABLE Server_Info (
  id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  url             VARCHAR(512),
  username        VARCHAR(128),
  password        VARCHAR(128)
);

Alter TABLE World ADD column git_server_id BIGINT;

ALTER TABLE World
    ADD FOREIGN KEY (git_server_id)
    REFERENCES Server_Info(id);
