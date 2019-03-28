package com.viadee.sonarquest.skillTree.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;


public interface UserSkillToSkillTreeUserRepository extends JpaRepository<UserSkillToSkillTreeUser, Long> {
    
    

}
