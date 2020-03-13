package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Skill;
import com.viadee.sonarquest.services.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(final SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping(value = "/{id}")
    public Skill getSkillById(@PathVariable(value = "id") final Long skillId) {
        return skillService.getSkillById(skillId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(@RequestBody final Skill skill) {
        return skillService.createSkill(skill);

    }

    @PutMapping(value = "/{id}")
    public Skill updateSkill(@PathVariable(value = "id") final Long skillId, @RequestBody final Skill skill) {
        return skillService.updateSkill(skillId, skill);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSkill(@PathVariable(value = "id") final Long skillId) {
        skillService.deleteSkillById(skillId);
    }

    @GetMapping(value = "artefact/{artefact_id}")
    public List<Skill> getSkillsForArtefact(@PathVariable(value = "artefact_id") final Long artefactId) {
        return skillService.getSkillsForArtefact(artefactId);
    }

}
