package com.viadee.sonarQuest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.LevelRepository;

@RestController
@RequestMapping("/level")
public class LevelController {

    private LevelRepository levelRepository;

    public LevelController(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Level getLevelById(@PathVariable(value = "id") final Long id) {
        return levelRepository.findById(id);
    }

}
