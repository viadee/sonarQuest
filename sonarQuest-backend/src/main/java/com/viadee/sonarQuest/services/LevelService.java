package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.LevelRepository;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public Level getLevelByUserXp(final Long xp) {
    	//find highest level within users xp
        return levelRepository.findFirstByMinXpIsLessThanEqualOrderByLevelDesc(xp, xp);
    }

    public Level findById(final Long id) {
        return levelRepository.findById(id);
    }
}
