package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.World;

import java.util.List;

public interface WorldRepository extends JpaRepository<World, Long> {

    World findByProject(String project);

    List<World> findByActiveTrue();
}
