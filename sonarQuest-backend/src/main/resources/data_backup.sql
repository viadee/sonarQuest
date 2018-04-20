INSERT INTO Level (min, max) VALUES
  (0, 10),
  (11, 30);

INSERT INTO Avatar_Class (name) VALUES
  ('Magier'),
  ('Krieger');

INSERT INTO Avatar_Race (name) VALUES
  ('Mensch'),
  ('Zwerg');

INSERT INTO Skill (name,type,value) VALUES
  ('Magier','GOLD',2),
  ('Krieger','XP',2),
  ('MehrGold','GOLD',1),
  ('MehrXp','XP',1);

INSERT INTO Avatar_Class_Skill (avatar_class_id,skill_id) VALUES
  (1,1),
  (2,2);

INSERT INTO Artefact (name,icon,price,level_id) VALUES
  ('Schwert','',50,1),
  ('Schild','',200,2);

INSERT INTO Artefact_Skill (artefact_id,skill_id) VALUES
  (1,3),
  (2,4);

INSERT INTO Developer (username, gold, xp, level_id,picture,about_me,avatar_class_id,avatar_race_id) VALUES
  ('sonarHero123', 10, 5, 1,'','Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et',1,2),
  ('sonarQuester123', 30, 20, 2,'','Ich bin der Allerbeste!',2,1);

INSERT INTO Developer_Artefact (developer_id,artefact_id) VALUES
  (1,1),
  (2,1),
  (2,2);

INSERT INTO World (name,project, active) VALUES
  ('SonarWorld', 'com.viadee:sonarWorld',TRUE);

INSERT INTO Adventure (title,gold, xp,status,story) VALUES
  ('SonarAdventure', 20,40,'OPEN','Dies ist eine Geschichte');

INSERT INTO Adventure_Developer (adventure_id,developer_id) VALUES
  (1,1);

INSERT INTO Quest (title,gold,xp,status,world_id,adventure_id,story) VALUES
  ('TestQuest1', 10,20, 'OPEN', 1,1,'Dies ist die erste Quest im Sonar Dungeon');

INSERT INTO Participation(quest_id,developer_id) VALUES
  (1,1);

INSERT INTO Task(title, gold, xp, status, quest_id,world_id, task_type, component, severity, type, debt, message, participation_id) VALUES
  ('TestStandardAufgabe1', 10, 12, 'OPEN',1,1,'STANDARD','TestComponent','BLOCKER','BUG','100',NULL,1),
  ('TestSonderAufgabe1', 30, 20, 'OPEN',1,1,'SPECIAL',NULL,NULL,NULL,NULL,'TestNachricht',1);

INSERT INTO Sonarconfig (name, sonar_server_url, sonar_project) VALUES
 ('World of Sonar Quest','https://sonar.intern.viadee.de', 'com.viadee:sonarQuest');
