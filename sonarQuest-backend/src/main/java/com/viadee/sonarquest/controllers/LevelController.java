package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.repositories.LevelRepository;

@RestController
@RequestMapping("/level")
public class LevelController {

    private LevelRepository levelRepository;

    public LevelController(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @GetMapping
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Level getLevelById(@PathVariable(value = "id") final Long id) {
        return levelRepository.findById(id);
    }

}
