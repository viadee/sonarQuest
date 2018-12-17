package com.viadee.sonarquest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.Skill;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill,Long> {

    List<Skill> findAll();
}
