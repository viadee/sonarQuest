INSERT INTO Sonar_Config (name, sonar_server_url) VALUES
  ('World of Sonar Quest', 'https://sonarcloud.io');

INSERT INTO SQLevel (sqlevel, min_xp, max_xp) VALUES
	(1,1,9),
	(2,10,29),
	(3,30,59),
	(4,60,99),
	(5,100,149),
	(6,150,209),
	(7,210,279),
	(8,280,359),
	(9,360,449),
	(10,450,549),
	(11,550,659),
	(12,660,779),
	(13,780,909),
	(14,910,1049),
	(15,1050,1199),
	(16,1200,1359),
	(17,1360,1529),
	(18,1530,1709),
	(19,1710,1899),
	(20,1900,2099),
	(21,2100,2309),
	(22,2310,2529),
	(23,2530,2759),
	(24,2760,2999),
	(25,3000,3249),
	(26,3250,3509),
	(27,3510,3779),
	(28,3780,4059),
	(29,4060,4349),
	(30,4350,4649),
	(31,4650,4959),
	(32,4960,5279),
	(33,5280,5609),
	(34,5610,5949),
	(35,5950,6299),
	(36,6300,6659),
	(37,6660,7029),
	(38,7030,7409),
	(39,7410,7799),
	(40,7800,8199),
	(41,8200,8609),
	(42,8610,9029),
	(43,9030,9459),
	(44,9460,9899),
	(45,9900,10349),
	(46,10350,10809),
	(47,10810,11279),
	(48,11280,11759),
	(49,11760,12249),
	(50,12250,12749),
	(51,12750,13259),
	(52,13260,13779),
	(53,13780,14309),
	(54,14310,14849),
	(55,14850,15399),
	(56,15400,15959),
	(57,15960,16529),
	(58,16530,17109),
	(59,17110,17699),
	(60,17700,18299),
	(61,18300,18909),
	(62,18910,19529),
	(63,19530,20159),
	(64,20160,20799),
	(65,20800,21449),
	(66,21450,22109),
	(67,22110,22779),
	(68,22780,23459),
	(69,23460,24149),
	(70,24150,24849),
	(71,24850,25559),
	(72,25560,26279),
	(73,26280,27009),
	(74,27010,27749),
	(75,27750,28499),
	(76,28500,29259),
	(77,29260,30029),
	(78,30030,30809),
	(79,30810,31599),
	(80,31600,32399),
	(81,32400,33209),
	(82,33210,34029),
	(83,34030,34859),
	(84,34860,35699),
	(85,35700,36549),
	(86,36550,37409),
	(87,37410,38279),
	(88,38280,39159),
	(89,39160,40049),
	(90,40050,40949),
	(91,40950,41859),
	(92,41860,42779),
	(93,42780,43709),
	(94,43710,44649),
	(95,44650,45599),
	(96,45600,46559),
	(97,46560,47529),
	(98,47530,48509),
	(99,48510,49499),
	(100,49500,NULL);

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
 (10, 'ACCESS', 'USER_WORLD_ASSIGNMENT'),
 (11, 'ACCESS', 'ACTIVE_WORLD_ACCESS'),
 (12,'URL', 'skilltree'),
 (13,'URL','innerskilltree');
 
INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,1),
 (1,2),
 (1,5),
 (1,6),
 (1,11),
 (2,1),
 (2,2),
 (2,3),
 (2,4),
 (2,5),
 (2,12),
 (2,13),
 (3,1),
 (3,2),
 (3,7),
 (3,8),
 (3,9),
 (3,10),
 (3,11);

INSERT INTO SQUser (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id)
 VALUES ('admin', '$2a$10$LoXVU5ODwytMz3Mh/Nft4.WaasCtwEuN6NEeJCER5X8o1ayCJHVxO', 3,   0,   0, 1, 'ava_hobbit1.jpg', 'Quick with the keys as well as the daggers, Eddie knows hidden paths to chambers of wisdom unknown!', 4, 2);
INSERT INTO SQUser (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id) 
 VALUES ('dev',   '$2a$10$LoXVU5ODwytMz3Mh/Nft4.WaasCtwEuN6NEeJCER5X8o1ayCJHVxO', 2, 0, 0, 1, 'ava_witch1.jpg', 'The little Witch is a pro with poisons and her broomstick.', 6, 1);
INSERT INTO SQUser (username, password, role_id, gold, xp, level_id, picture, about_me, avatar_class_id, avatar_race_id)
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

--INSERT INTO Sonar_Rule(rule_name,rule_key,user_skill_id) VALUES 
--('Servlets should not have mutable instance fields','squid:S2226',1),
--('Sections of code should not be commented out','xml:S125',2);

INSERT INTO User_Skill_Group(group_name,is_root) VALUES 
('Java-Basics I',1),
('Build and Debugging',1),
('Style & Structure',1),
('Java Basics II',0),
('Network Programming',0),
('Threading',0),
('Security',0),
('Spring Framework',0),
('File Handling',0);

INSERT INTO User_Skill_Group_Following (user_skill_group_id,following_user_skill_group_id) VALUES
(1,4),
(4,5),
(4,6),
(4,7),
(4,9),
(5,8);

INSERT INTO User_Skill(skill_name,description,is_root,required_repetitions,user_skill_group_id) VALUES 
('Java-Basics','Fundamental java basic skills',1,3,1),
('Primitive types','Easy data types',0,3,1),
('Packages','Structure your code project',0,3,1),
('Java Doc','Stay informed! Document your code',0,3,1),
('Scopes','Where is the code reachable',0,3,1),
('Static','Global and unique useable',0,3,1),
('Floating point numbers','More flexible numbers',0,3,1),
('Switch-case','asd',0,3,1),
('Operators','+ - * / =',0,3,1),
('If','If-statement',0,3,1),
('Methods','Build your own functionality',0,3,1);

-- INSERT INTO User_Skill_TO_Sonar_Rule (user_skill_id,sonar_rule_id) VALUES (1,1), (2,1), (3,1);


INSERT INTO User_Skill_Following (user_skill_id,following_user_skill_id) VALUES
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(2,7),
(2,8),
(2,9),
(9,10),
(10,11),
(6,11);


INSERT INTO skill_tree_user (mail) VALUES ('test1@test.de');

INSERT INTO user_skill_to_skill_tree_user (learned_on, repeats, user_skill_id, skill_tree_user_id) VALUES (NULL,1,1,1);











