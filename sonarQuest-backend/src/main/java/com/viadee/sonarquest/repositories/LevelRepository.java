package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {

	Level findFirstByMinXpIsLessThanEqualOrderByLevelNumberDesc(Long xp);

	Level findFirstByLevelNumberOrderByLevelNumberDesc(int levelNumber);
}
