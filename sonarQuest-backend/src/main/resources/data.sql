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

INSERT INTO Skill (name,type,value) VALUES
  ('Staffwielding','GOLD',2),
  ('Swordfighting','XP',2),
  ('Golden Hands','GOLD',1),
  ('Seasoned','XP',1),
  ('Musical', 'GOLD',3);

INSERT INTO Avatar_Class_Skill (avatar_class_id,skill_id) VALUES
  (1,1),
  (2,2),
  (3,4),
  (4,3),
  (5,5);

INSERT INTO Artefact (name,icon,price,level_id,quantity,description) VALUES
  ('Swift Sword','ra-spinning-sword',50,1,5,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Small Shield','ra-fire-shield',200,2,8,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Blue Dagger of the Song','ra-plain-dagger',150,2,10,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores'),
  ('Axe of Doom','ra-battered-axe',75,1,3,'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores');


INSERT INTO Artefact_Skill (artefact_id,skill_id) VALUES
  (1,3),
  (2,4),
  (3,5);

INSERT INTO Developer (username, gold, xp, level_id,picture,about_me,avatar_class_id,avatar_race_id) VALUES
  ('Eddie Tor', 10, 5, 1,'ava_hobbit1.jpg','Quick with the keys as well as the daggers, Eddie knows hidden paths to chambers of wisdom unknown!',4,2),
  ('Ringo Rockstar', 30, 20, 2,'ava_barbarian1.jpg','The mighty barbarian has the strength and the tools to do the job. Brute force ftw!',2,3),
  ('Wendy Witch', 150, 200, 2,'ava_witch1.jpg','The little Witch is a pro with poisons and her broomstick.',6,1),
  ('Mike Magician', 18, 15, 2,'ava_mage1.jpg','Raised in the Lands of the North, wisdomey and experienced, this talented Magician will find a solution for everything. And sometimes it might really look like magic!',1,1);

INSERT INTO Developer_Artefact (developer_id,artefact_id) VALUES
  (1,3),
  (1,2),
  (1,4),
  (2,4),
  (3,1);

INSERT INTO Sonarconfig (name, sonar_server_url, sonar_project) VALUES
 ('World of Sonar Quest','https://sonar.intern.viadee.de', 'com.viadee:sonarQuest');


