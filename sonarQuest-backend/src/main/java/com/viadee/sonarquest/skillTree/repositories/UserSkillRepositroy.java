package com.viadee.sonarquest.skillTree.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skillTree.entities.UserSkill;


public interface UserSkillRepositroy extends JpaRepository<UserSkill, Long> {
    
    //UserSkill findById(Long id);
    @Query("SELECT u FROM UserSkill u WHERE isRoot = :isRoot")
    public List<UserSkill> findAllRootUserSkills(@Param("isRoot") boolean isRoot);
    
    @Query("SELECT u FROM UserSkill u WHERE userSkillGroup.id = :id")
	public List<UserSkill> findUserSkillsByGroup(@Param("id") Long id);

}
