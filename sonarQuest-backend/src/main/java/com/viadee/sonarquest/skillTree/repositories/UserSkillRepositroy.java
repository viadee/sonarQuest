package com.viadee.sonarquest.skillTree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.skillTree.entities.UserSkill;


public interface UserSkillRepositroy extends JpaRepository<UserSkill, Long> {
    
    //UserSkill findById(Long id);

}
