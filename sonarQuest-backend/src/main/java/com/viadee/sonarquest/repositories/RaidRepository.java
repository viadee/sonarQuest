package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.World;

public interface RaidRepository extends JpaRepository<Raid, Long> {
	 List<Raid> findByWorld(World world);
	 List<Raid> findByWorldAndVisible(World world, boolean visible);
}
