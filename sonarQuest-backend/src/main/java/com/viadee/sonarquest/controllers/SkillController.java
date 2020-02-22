package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.Skill;
import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.services.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
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
