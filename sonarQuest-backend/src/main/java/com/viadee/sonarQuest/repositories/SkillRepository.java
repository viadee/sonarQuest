package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill,Long> {

    List<Skill> findAll();
}
