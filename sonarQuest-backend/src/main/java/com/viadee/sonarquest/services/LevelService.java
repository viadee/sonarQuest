package com.viadee.sonarquest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.repositories.LevelRepository;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public Level getLevelByUserXp(final Long xp) {
        // find highest level within users xp
        return levelRepository.findFirstByMinXpIsLessThanEqualOrderByLevelDesc(xp);
    }

    public Level findById(final Long id) {
        return levelRepository.findById(id);
    }

    public void createLevel(Level newLevel) {
        levelRepository.save(newLevel);
    }

    public Level findByLevel(int level) {
        return levelRepository.findFirstByLevelOrderByLevelDesc(level);
    }
}
