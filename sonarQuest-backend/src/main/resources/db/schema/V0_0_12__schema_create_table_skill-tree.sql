

CREATE TABLE user_skill (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description varchar(256),
    skill_name varchar(256),
    is_root 	BOOLEAN,
    required_repetitions INT,
    user_skill_group_id BIGINT,
     );
     
CREATE TABLE user_skill_group (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,    
    group_name varchar(256),
    is_root BOOLEAN,
    group_icon varchar(256)
    
     );
     
CREATE TABLE sonar_rule (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rule_key varchar(256),
    rule_name varchar(256),
    added_at TIMESTAMP,
    user_skill_id BIGINT
    );
   
CREATE TABLE user_skill_following (
	id 							BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_skill_id				BIGINT NOT NULL,
	following_user_skill_id		BIGINT NOT NULL,
	FOREIGN KEY (user_skill_id) 			REFERENCES User_Skill(id),
	FOREIGN KEY (following_user_skill_id) 	REFERENCES User_Skill(id)   
    );
    
CREATE TABLE user_skill_group_following (
	id 							BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_skill_group_id			BIGINT NOT NULL,
	following_user_skill_group_id		BIGINT NOT NULL,
	FOREIGN KEY (user_skill_group_id) 			REFERENCES User_Skill_Group(id),
	FOREIGN KEY (following_user_skill_group_id) 	REFERENCES User_Skill_Group(id)   
    );
    
 
CREATE TABLE user_skill_previous (
	id 							BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_skill_id				BIGINT NOT NULL,
	previous_user_skill_id		BIGINT NOT NULL,
	FOREIGN KEY (user_skill_id) 			REFERENCES User_Skill(id),
	FOREIGN KEY (previous_user_skill_id) 	REFERENCES User_Skill(id)
    );
    
--     Currently needed
-- CREATE TABLE user_skill_to_sonar_rule (
--     id 							BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
-- 	user_skill_id				BIGINT NOT NULL,
-- 	sonar_rule_id				BIGINT NOT NULL,
-- 	FOREIGN KEY (user_skill_id) REFERENCES User_Skill(id),
-- 	FOREIGN KEY (sonar_rule_id) REFERENCES Sonar_Rule(id)
--     );
--    
    CREATE TABLE skill_tree_user (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mail varchar(256)
    );  
    
    CREATE TABLE user_skill_to_skill_tree_user (
    id 							BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    repeats						INT,
    learned_on					TIMESTAMP,
    score						FLOAT,
	user_skill_id				BIGINT NOT NULL,
	skill_tree_user_id			BIGINT NOT NULL,
	FOREIGN KEY (user_skill_id) REFERENCES User_Skill(id),
	FOREIGN KEY (skill_tree_user_id) REFERENCES Skill_Tree_User(id)
    );