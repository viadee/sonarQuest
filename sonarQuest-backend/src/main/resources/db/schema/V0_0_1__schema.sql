CREATE TABLE Level (
  id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  min BIGINT,
  max BIGINT
);

CREATE TABLE Avatar_Class (
  id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE Avatar_Race (
  id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE Skill (
  id    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(256) NOT NULL,
  type  VARCHAR(64)  NOT NULL,
  value BIGINT       NOT NULL
);

CREATE TABLE Avatar_Class_Skill (
  avatar_class_id BIGINT NOT NULL,
  skill_id        BIGINT NOT NULL
);

ALTER TABLE Avatar_Class_Skill
  ADD PRIMARY KEY (avatar_class_id, skill_id);
ALTER TABLE Avatar_Class_Skill
  ADD FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class (id);
ALTER TABLE Avatar_Class_Skill
  ADD FOREIGN KEY (skill_id) REFERENCES Skill (id);

CREATE TABLE Artefact (
  id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(64) NOT NULL UNIQUE,
  icon        VARCHAR(256),
  price       BIGINT,
  level_id    BIGINT,
  quantity    BIGINT,
  description VARCHAR(256)
);

CREATE TABLE Ui_Design (
  id           BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name         VARCHAR(64) NOT NULL UNIQUE,
  developer_id BIGINT
);

ALTER TABLE Artefact
  ADD FOREIGN KEY (level_id) REFERENCES Level (id);

CREATE TABLE Artefact_Skill (
  artefact_id BIGINT NOT NULL,
  skill_id    BIGINT NOT NULL
);

ALTER TABLE Artefact_Skill
  ADD PRIMARY KEY (artefact_id, skill_id);
ALTER TABLE Artefact_Skill
  ADD FOREIGN KEY (artefact_id) REFERENCES Artefact (id);
ALTER TABLE Artefact_Skill
  ADD FOREIGN KEY (skill_id) REFERENCES Skill (id);

CREATE TABLE Developer (
  id              BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username        VARCHAR(64) NOT NULL UNIQUE,
  gold            BIGINT,
  xp              BIGINT,
  level_id        BIGINT,
  picture         VARCHAR(256),
  about_me        VARCHAR(256),
  avatar_class_id BIGINT,
  avatar_race_id  BIGINT,
  world_id        BIGINT,
  deleted         BOOLEAN              DEFAULT FALSE
);

ALTER TABLE Developer
  ADD FOREIGN KEY (level_id) REFERENCES Level (id);
ALTER TABLE Developer
  ADD FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class (id);
ALTER TABLE Developer
  ADD FOREIGN KEY (avatar_race_id) REFERENCES Avatar_Race (id);

CREATE TABLE Developer_Artefact (
  developer_id BIGINT NOT NULL,
  artefact_id  BIGINT NOT NULL
);

ALTER TABLE Developer_Artefact
  ADD PRIMARY KEY (developer_id, artefact_id);
ALTER TABLE Developer_Artefact
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);
ALTER TABLE Developer_Artefact
  ADD FOREIGN KEY (artefact_id) REFERENCES Artefact (id);

CREATE TABLE Task (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title            VARCHAR(256),
  gold             BIGINT,
  xp               BIGINT,
  status           VARCHAR(256),
  quest_id         BIGINT,
  world_id         BIGINT,
  task_type        VARCHAR(256),
  task_key         VARCHAR(256),
  component        VARCHAR(256),
  severity         VARCHAR(256),
  type             VARCHAR(256),
  debt             INTEGER,
  message          VARCHAR(256),
  participation_id BIGINT,
  issue_key        VARCHAR(256)
);

CREATE TABLE World (
  id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(64),
  image   VARCHAR(255),
  project VARCHAR(64),
  active  BOOLEAN
);

CREATE TABLE Adventure (
  id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title    VARCHAR(64),
  gold     BIGINT,
  xp       BIGINT,
  status   VARCHAR(64),
  story    VARCHAR(256),
  world_id BIGINT
);

ALTER TABLE Adventure
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE Adventure_Developer (
  adventure_id BIGINT NOT NULL,
  developer_id BIGINT NOT NULL
);

ALTER TABLE Adventure_Developer
  ADD PRIMARY KEY (adventure_id, developer_id);
ALTER TABLE Adventure_Developer
  ADD FOREIGN KEY (adventure_id) REFERENCES Adventure (id);
ALTER TABLE Adventure_Developer
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);

CREATE TABLE Quest (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title        VARCHAR(64),
  gold         BIGINT,
  xp           BIGINT,
  status       VARCHAR(64),
  world_id     BIGINT,
  adventure_id BIGINT,
  story        VARCHAR(256),
  image        VARCHAR(256)
);

ALTER TABLE Quest
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Quest
  ADD FOREIGN KEY (adventure_id) REFERENCES Adventure (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Task
  ADD FOREIGN KEY (quest_id) REFERENCES Quest (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE Task
  ADD FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE Participation (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  quest_id     BIGINT NOT NULL,
  developer_id BIGINT NOT NULL
);

ALTER TABLE Participation
  ADD FOREIGN KEY (developer_id) REFERENCES Developer (id);
ALTER TABLE Participation
  ADD FOREIGN KEY (quest_id) REFERENCES Quest (id);


ALTER TABLE Task
  ADD FOREIGN KEY (participation_id) REFERENCES Participation (id) ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE Sonar_Config (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name             VARCHAR(128),
  sonar_server_url VARCHAR(128),
  sonar_project    VARCHAR(128)
);

CREATE TABLE Role (
	id			BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name		VARCHAR(128)
);

CREATE TABLE User (
	id				BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username		VARCHAR(64) NOT NULL UNIQUE,
	password		VARCHAR(60) NOT NULL,
	role_id			BIGINT,
	picture         VARCHAR(256),
	about_me        VARCHAR(256),
	avatar_class_id BIGINT,
	avatar_race_id  BIGINT,
	gold            BIGINT,
	xp              BIGINT,
	level_id        BIGINT,
	FOREIGN KEY (role_id) REFERENCES Role(id),
	FOREIGN KEY (level_id) REFERENCES Level(id),
	FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class(id),
	FOREIGN KEY (avatar_race_id) REFERENCES Avatar_Race(id)
);

CREATE TABLE UserToWorld (
	id 				BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id			BIGINT,
	world_id		BIGINT,
	FOREIGN KEY (user_id) REFERENCES User(id),
	FOREIGN KEY (world_id) REFERENCES World(id)
);

