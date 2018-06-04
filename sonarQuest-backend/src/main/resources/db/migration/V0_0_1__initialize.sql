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


INSERT INTO Level (min, max) VALUES
  (0, 10),
  (11, 30);

INSERT INTO Avatar_Class (name) VALUES
  ('Magician'),
  ('Warrior'),
  ('Adventurer'),
  ('Thief'),
  ('Bard'),
  ('Witch');

INSERT INTO Avatar_Race (name) VALUES
  ('Human'),
  ('Dwarf'),
  ('Barbarian'),
  ('Elf');

INSERT INTO Skill (name, type, value) VALUES
  ('Staffwielding', 'GOLD', 2),
  ('Swordfighting', 'XP', 2),
  ('Golden Hands', 'GOLD', 1),
  ('Seasoned', 'XP', 1),
  ('Musical', 'GOLD', 3);

INSERT INTO Avatar_Class_Skill (avatar_class_id, skill_id) VALUES
  (1, 1),
  (2, 2),
  (3, 4),
  (4, 3),
  (5, 5);

INSERT INTO Artefact (name, icon, price, level_id, quantity, description) VALUES
  ('Swift Sword', 'ra-spinning-sword', 50, 1, 5,
   'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Small Shield', 'ra-fire-shield', 200, 2, 8,
   'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Blue Dagger of the Song', 'ra-plain-dagger', 150, 2, 10,
   'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Axe of Doom', 'ra-battered-axe', 75, 1, 3,
   'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores');


INSERT INTO Artefact_Skill (artefact_id, skill_id) VALUES
  (1, 3),
  (2, 4),
  (3, 5);

INSERT INTO Developer (username, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id) VALUES
  ('Eddie Tor', 10, 5, 1, 'ava_hobbit1.jpg',
   'Quick with the keys as well as the daggers, Eddie knows hidden paths to chambers of wisdom unknown!', 4, 2),
  ('Ringo Rockstar', 30, 20, 2, 'ava_barbarian1.jpg',
   'The mighty barbarian has the strength and the tools to do the job. Brute force ftw!', 2, 3),
  ('Wendy Witch', 150, 200, 2, 'ava_witch1.jpg', 'The little Witch is a pro with poisons and her broomstick.', 6, 1),
  ('Mike Magician', 18, 15, 2, 'ava_mage1.jpg',
   'Raised in the Lands of the North, wisdomey and experienced, this talented Magician will find a solution for everything. And sometimes it might really look like magic!',
   1, 1);

INSERT INTO Developer_Artefact (developer_id, artefact_id) VALUES
  (1, 3),
  (1, 2),
  (1, 4),
  (2, 4),
  (3, 1);

INSERT INTO Sonar_Config (name, sonar_server_url, sonar_project) VALUES
  ('World of Sonar Quest', 'https://sonar.intern.viadee.de', 'com.viadee:sonarQuest');




