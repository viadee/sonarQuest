package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.World;

public interface WorldRepository extends JpaRepository<World, Long> {

    World findByProject(String project);

    List<World> findByActiveTrue();

	World findFirst1By();
}
