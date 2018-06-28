INSERT INTO Sonar_Config (name, sonar_server_url) VALUES
  ('World of Sonar Quest', 'https://sonarcloud.io');

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

  
INSERT INTO Role (id, name) VALUES(1,'GAMEMASTER');
INSERT INTO Role (id, name) VALUES(2,'DEVELOPER');
INSERT INTO Role (id, name) VALUES(3,'ADMIN');

INSERT INTO Permission (id, type, permission) VALUES
 (1, 'URL', 'start'),
 (2, 'URL', 'myAvatar'),
 (3, 'URL', 'adventures'),
 (4, 'URL', 'quests'),
 (5, 'URL', 'marketplace'),
 (6, 'URL', 'gamemaster'),
 (7, 'URL', 'admin'),
 (8, 'ACCESS', 'FULL_USER_ACCESS'),
 (9, 'ACCESS', 'FULL_WORLD_ACCESS'),
 (10, 'ACCESS', 'USER_WORLD_AISSIGNMENT'),
 (11, 'ACCESS', 'ACTIVE_WORLD_ACCESS'),
 ;
 
INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,1),
 (1,2),
 (1,6),
 (2,1),
 (2,2),
 (2,3),
 (2,4),
 (3,1),
 (3,2),
 (3,7),
 (3,8),
 (3,9),
 (3,10),
 (1,11),
 (3,11),
 ;

INSERT INTO User (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id)
 VALUES ('admin', '$2a$10$LoXVU5ODwytMz3Mh/Nft4.WaasCtwEuN6NEeJCER5X8o1ayCJHVxO', 3,   0,   0, 1, 'ava_hobbit1.jpg', 'Quick with the keys as well as the daggers, Eddie knows hidden paths to chambers of wisdom unknown!', 4, 2);
INSERT INTO User (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id) 
 VALUES ('dev',   '$2a$10$LoXVU5ODwytMz3Mh/Nft4.WaasCtwEuN6NEeJCER5X8o1ayCJHVxO', 2, 150, 200, 2, 'ava_witch1.jpg', 'The little Witch is a pro with poisons and her broomstick.', 6, 1);
INSERT INTO User (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id)
 VALUES ('gm',    '$2a$10$LoXVU5ODwytMz3Mh/Nft4.WaasCtwEuN6NEeJCER5X8o1ayCJHVxO', 1,   0,   0, 1, 'ava_barbarian1.jpg', 'The mighty barbarian has the strength and the tools to do the job. Brute force ftw!', 2, 3);

INSERT INTO Ui_Design (name, user_id) VALUES ('light',1);
INSERT INTO Ui_Design (name, user_id) VALUES ('light',2);
INSERT INTO Ui_Design (name, user_id) VALUES ('light',3);


INSERT INTO User_Artefact (user_id, artefact_id) VALUES
  (1, 3),
  (1, 2),
  (1, 4),
  (2, 4),
  (3, 1);