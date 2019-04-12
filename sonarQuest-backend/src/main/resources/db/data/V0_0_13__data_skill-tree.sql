INSERT INTO Sonar_Rule(rule_name,rule_key,user_skill_id) VALUES 
('Servlets should not have mutable instance fields','squid:S2226',1),
('Sections of code should not be commented out','xml:S125',1),
('Source files should not have any duplicated blocks','common-java:DuplicatedBlocks',7),
('Tests should include assertions','squid:S2699',11);




INSERT INTO User_Skill_Group(group_name,is_root, group_icon) VALUES 
('Java-Basics I',1,null),
('Build and Debugging',1,null),
('Style & Structure',1,null),
('Java Basics II',0,null),
('Network Programming',0, null),
('Threading',0,'ra-fairy-wand'),
('Security',0,' ra-shield'),
('Spring Framework',0,null),
('File Handling',0,null);

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

INSERT INTO User_Skill_Previous (user_skill_id,previous_user_skill_id) VALUES
(2,1),
(3,1),
(4,1),
(5,1),
(6,1),
(7,2),
(8,2),
(9,2),
(10,9),
(11,10),
(11,6);


--INSERT INTO skill_tree_user (mail) VALUES ('test1@test.de');

--INSERT INTO user_skill_to_skill_tree_user (learned_on, repeats, user_skill_id, skill_tree_user_id) VALUES (NULL,1,1,1);