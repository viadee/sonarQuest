CREATE TABLE Sonar_Config (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name             VARCHAR(128),
  sonar_server_url VARCHAR(128),
  http_basic_auth_username VARCHAR(128),
  http_basic_auth_password VARCHAR(128)
);

CREATE TABLE SQLevel (
  id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sqlevel int,
  min_xp BIGINT,
  max_xp BIGINT
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
  skill_id        BIGINT NOT NULL,
  PRIMARY KEY (avatar_class_id, skill_id),
  FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class (id),
  FOREIGN KEY (skill_id) REFERENCES Skill (id)
);

CREATE TABLE Artefact (
  id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(64) NOT NULL UNIQUE,
  icon        VARCHAR(256),
  price       BIGINT,
  level_id    BIGINT,
  quantity    BIGINT,
  description VARCHAR(256),
  FOREIGN KEY (level_id) REFERENCES SQLevel (id)
);

CREATE TABLE Artefact_Skill (
  artefact_id BIGINT NOT NULL,
  skill_id    BIGINT NOT NULL,
  PRIMARY KEY (artefact_id, skill_id),
  FOREIGN KEY (artefact_id) REFERENCES Artefact (id),
  FOREIGN KEY (skill_id) REFERENCES Skill (id)
);

CREATE TABLE World (
  id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(255),
  image   VARCHAR(255),
  project VARCHAR(255),
  active  BOOLEAN
);

CREATE TABLE Adventure (
  id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title    VARCHAR(64),
  gold     BIGINT,
  xp       BIGINT,
  status   VARCHAR(64),
  story    VARCHAR(256),
  world_id BIGINT,
  FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE Raid (
  id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  raid_type 	VARCHAR(256),
  title    		VARCHAR(64),
  gold     		BIGINT,
  xp       		BIGINT,
  status   		VARCHAR(64),
  description   VARCHAR(256),
  startdate 	DATE,
  enddate 		DATE,
  visible 		BOOLEAN,
  world_id 		BIGINT,
  monster_name	VARCHAR(256),
  monster_image	VARCHAR(256),
  FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE quality_raid (
 id       			BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 sonar_qube_status  VARCHAR(64),
 amount_Of_Error 	INTEGER,
 amount_Of_Warn 	INTEGER,
 amount_Of_Ok 		INTEGER,
);

CREATE TABLE Quest (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title        VARCHAR(64),
  gold         BIGINT,
  xp           BIGINT,
  status       VARCHAR(64),
  world_id     BIGINT,
  adventure_id BIGINT,
  raid_id 	   BIGINT,
  story        VARCHAR(256),
  image        VARCHAR(256),
  
  FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (adventure_id) REFERENCES Adventure (id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (raid_id) REFERENCES Raid (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Role (
	id			BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name		VARCHAR(128)
);

CREATE TABLE Permission (
	id			BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	permission	VARCHAR(128),
	type  		VARCHAR(24)
);

CREATE TABLE Role_To_Permission (
	id 					BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	role_id				BIGINT,
	permission_id		BIGINT,
	FOREIGN KEY (role_id) REFERENCES Role(id),
	FOREIGN KEY (permission_id) REFERENCES Permission(id)
);

CREATE TABLE SQUser (
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
	current_world_id BIGINT,
	FOREIGN KEY (role_id) REFERENCES Role(id),
	FOREIGN KEY (level_id) REFERENCES SQLevel(id),
	FOREIGN KEY (avatar_class_id) REFERENCES Avatar_Class(id),
	FOREIGN KEY (avatar_race_id) REFERENCES Avatar_Race(id),
	FOREIGN KEY (current_world_id) REFERENCES World(id)
);

CREATE TABLE User_To_World (
	id 				BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id			BIGINT,
	world_id		BIGINT,
	FOREIGN KEY (user_id) REFERENCES SQUser(id),
	FOREIGN KEY (world_id) REFERENCES World(id)
);

CREATE TABLE User_Artefact (
  user_id BIGINT NOT NULL,
  artefact_id  BIGINT NOT NULL,
  PRIMARY KEY (user_id, artefact_id),
  FOREIGN KEY (user_id) REFERENCES SQUser (id),
  FOREIGN KEY (artefact_id) REFERENCES Artefact (id)
);

CREATE TABLE Adventure_User (
  adventure_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (adventure_id, user_id),
  FOREIGN KEY (adventure_id) REFERENCES Adventure (id),
  FOREIGN KEY (user_id) REFERENCES SQUser (id)
);

CREATE TABLE Participation (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  participation_type  VARCHAR(256),
  quest_id     BIGINT,
  raid_id     BIGINT,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES SQUser (id),
  FOREIGN KEY (quest_id) REFERENCES Quest (id),
  FOREIGN KEY (raid_id) REFERENCES Raid (id),
);

CREATE TABLE Ui_Design (
  id      BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(64) NOT NULL,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES SQUser (id) 
);


CREATE TABLE Task (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  task_type        VARCHAR(256),
  title            VARCHAR(256),
  gold             BIGINT,
  xp               BIGINT,
  status           VARCHAR(256),
  task_key         VARCHAR(256),
  quest_id         BIGINT,
  raid_id          BIGINT,
  world_id         BIGINT,
  participation_id BIGINT,
  
  FOREIGN KEY (quest_id) REFERENCES Quest (id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (raid_id) REFERENCES Raid (id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (world_id) REFERENCES World (id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (participation_id) REFERENCES Participation (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Special_Task (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  message          VARCHAR(256)
);

CREATE TABLE Standard_Task (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  component        VARCHAR(256),
  severity         VARCHAR(256),
  type             VARCHAR(256),
  debt             INTEGER,
  issue_key        VARCHAR(256),
  issue_rule	   VARCHAR(256)
);

CREATE TABLE Condition_Task (
  id                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  metric_Key        VARCHAR(256),
  comparator        VARCHAR(256),
  accepted_Value    DOUBLE,
  error_Threshold   DOUBLE
);


