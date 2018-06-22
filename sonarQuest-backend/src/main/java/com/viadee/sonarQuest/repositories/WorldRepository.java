package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.World;

public interface WorldRepository extends JpaRepository<World, Long> {

    World findByProject(String project);

}
