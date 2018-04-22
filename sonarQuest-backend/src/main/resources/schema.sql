CREATE TABLE Level (
  id   INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  min  BIGINT,
  max  BIGINT
);

CREATE TABLE Avatar_Class (
  id   INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE Avatar_Race (
  id   INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE Skill (
  id   INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  type VARCHAR(64) NOT NULL ,
  value INTEGER NOT NULL 
);

CREATE TABLE Avatar_Class_Skill (
  avatar_class_id INTEGER NOT NULL,
  skill_id        INTEGER NOT NULL
);

ALTER TABLE Avatar_Class_Skill
  ADD PRIMARY KEY (avatar_class_id, skill_id);
ALTER TABLE Avatar_Class_Skill
  ADD FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class (id);
ALTER TABLE Avatar_Class_Skill
  ADD FOREIGN KEY (skill_id) REFERENCES Skill (id);

CREATE TABLE Artefact (
  id       INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(64) NOT NULL UNIQUE,
  icon     VARCHAR(256),
  price    BIGINT,
  level_id INTEGER,
  quantity INTEGER ,
  description VARCHAR(256)
);

ALTER TABLE Artefact
  ADD FOREIGN KEY (level_id) REFERENCES Level (id);

CREATE TABLE Artefact_Skill (
  artefact_id INTEGER NOT NULL,
  skill_id    INTEGER NOT NULL
);

ALTER TABLE Artefact_Skill
  ADD PRIMARY KEY (artefact_id, skill_id);
ALTER TABLE Artefact_Skill
  ADD FOREIGN KEY (artefact_id) REFERENCES Artefact (id);
ALTER TABLE Artefact_Skill
  ADD FOREIGN KEY (skill_id) REFERENCES Skill (id);


CREATE TABLE Developer (
  id              INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username        VARCHAR(64) NOT NULL UNIQUE,
  gold            BIGINT,
  xp              BIGINT,
  level_id        INTEGER,
  picture         VARCHAR(256),
  about_me         VARCHAR(256),
  avatar_class_id INTEGER,
  avatar_race_id  INTEGER,
  deleted	  BOOLEAN	DEFAULT FALSE
);

ALTER TABLE Developer
  ADD FOREIGN KEY (level_id) REFERENCES Level (id);
ALTER TABLE Developer
  ADD FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class (id);
ALTER TABLE Developer
  ADD FOREIGN KEY (avatar_race_id) REFERENCES Avatar_Race (id);

CREATE TABLE Developer_Artefact (
  developer_id INTEGER NOT NULL,
  artefact_id  INTEGER NOT NULL
);

ALTER TABLE Developer_Artefact
  ADD PRIMARY KEY (developer_id, artefact_id);
ALTER TABLE Developer_Artefact
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);
ALTER TABLE Developer_Artefact
  ADD FOREIGN KEY (artefact_id) REFERENCES Artefact (id);

CREATE TABLE Task (
  id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title     VARCHAR(256),
  gold      BIGINT,
  xp        BIGINT,
  status    VARCHAR(256),
  quest_id  INTEGER,
  world_id  INTEGER,
  task_type VARCHAR(256),
  key VARCHAR(256),
  component VARCHAR(256),
  severity  VARCHAR(256),
  type      VARCHAR(256),
  debt      VARCHAR(256),
  message   VARCHAR(256),
  participation_id INTEGER,
  issue_key VARCHAR(256)
);

CREATE TABLE World (
  id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(64),
  project    VARCHAR(64),
  active    BOOLEAN
);

CREATE TABLE Adventure (
  id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title     VARCHAR(64),
  gold      BIGINT,
  xp        BIGINT,
  status    VARCHAR(64),
  story     VARCHAR(256),
  world_id  INTEGER,
);

ALTER TABLE Adventure
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE Adventure_Developer (
  adventure_id  INTEGER NOT NULL,
  developer_id INTEGER NOT NULL
);

ALTER TABLE Adventure_Developer
  ADD PRIMARY KEY (adventure_id, developer_id);
ALTER TABLE Adventure_Developer
  ADD FOREIGN KEY (adventure_id) REFERENCES Adventure (id);
ALTER TABLE Adventure_Developer
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);


CREATE TABLE Quest (
  id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title     VARCHAR(64),
  gold      BIGINT,
  xp        BIGINT,
  status    VARCHAR(64),
  world_id  INTEGER,
  adventure_id INTEGER,
  story     VARCHAR(256),
  image     VARCHAR(256)
);

ALTER TABLE Quest
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Quest
  ADD FOREIGN KEY (adventure_id) REFERENCES Adventure (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Task
  ADD FOREIGN KEY (quest_id) REFERENCES Quest (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Task
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE ;

CREATE TABLE Participation (
  id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  quest_id  INTEGER NOT NULL,
  developer_id INTEGER NOT NULL
);

ALTER TABLE Participation
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);
ALTER TABLE Participation
  ADD FOREIGN KEY (quest_id) REFERENCES Quest (id);


ALTER TABLE Task
  ADD FOREIGN KEY (participation_id )REFERENCES Participation (id) ON DELETE SET NULL ON UPDATE CASCADE ;

CREATE TABLE Sonarconfig (
	id        INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(128),
	sonar_server_url VARCHAR(128),
	sonar_project VARCHAR(128)
);


