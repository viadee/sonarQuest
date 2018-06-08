package com.viadee.sonarQuest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Level createLevel(@RequestBody final Level level) {
        return levelRepository.save(level);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Level updateLevel(@PathVariable(value = "id") final Long id, @RequestBody final Level data) {
        Level level = levelRepository.findById(id);
        if (level != null) {
            level.setMin(data.getMin());
            level.setMax(data.getMax());
            level = levelRepository.save(level);
        }
        return level;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteLevel(@PathVariable(value = "id") final Long id) {
        final Level level = levelRepository.findById(id);
        if (level != null) {
            levelRepository.delete(level);
        }
    }

}
