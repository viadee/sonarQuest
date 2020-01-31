package com.viadee.sonarquest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.repositories.LevelRepository;

@Service
public class LevelService {

	private final LevelRepository levelRepository;

	public LevelService(LevelRepository levelRepository) {
		this.levelRepository = levelRepository;
	}

	public Level getLevelByUserXp(final Long xp) {
		// find highest level within users XP
		return levelRepository.findFirstByMinXpIsLessThanEqualOrderByLevelNumberDesc(xp);
	}

	public Level findById(final Long id) throws ResourceNotFoundException {
		return levelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	public void createLevel(Level newLevel) {
		levelRepository.save(newLevel);
	}

	public Level findByLevel(int levelNumber) {
		return levelRepository.findFirstByLevelNumberOrderByLevelNumberDesc(levelNumber);
	}
}
