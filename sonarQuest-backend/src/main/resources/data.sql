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

INSERT INTO Artefact (name,icon,price,level_id,quantity,description) VALUES
  ('Schwert','ra-sword',50,1,5,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Schild','ra-fire-shield',200,2,8,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores');

INSERT INTO Artefact_Skill (artefact_id,skill_id) VALUES
  (1,3),
  (2,4);

INSERT INTO Developer (username, gold, xp, level_id,picture,about_me,avatar_class_id,avatar_race_id) VALUES
  ('sonarHero123', 10, 5, 1,'assets/images/quest/hero2.jpg','Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et',1,2),
  ('sonarQuester123', 30, 20, 2,'assets/images/quest/hero4.jpg','Ich bin der Allerbeste!',2,1),
  ('sonarWarrior', 18, 15, 2,'assets/images/quest/hero6.jpg','BADABOOOOM',2,1);

INSERT INTO Developer_Artefact (developer_id,artefact_id) VALUES
  (1,1),
  (2,1),
  (2,2);




