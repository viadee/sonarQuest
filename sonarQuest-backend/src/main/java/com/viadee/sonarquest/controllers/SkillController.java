package com.viadee.sonarquest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Skill;
import com.viadee.sonarquest.repositories.SkillRepository;
import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.services.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ArtefactService artefactService;

    @Autowired
    private SkillService skillService;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Skill getSkillById(@PathVariable(value = "id") final Long id) {
        return skillRepository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(@RequestBody final Skill skillDto) {
        return skillRepository.save(skillDto);

    }

    @PutMapping(value = "/{id}")
    public Skill updateSkill(@PathVariable(value = "id") final Long id, @RequestBody final Skill data) {
        Skill skill = skillRepository.findOne(id);
        if (skill != null) {
            skill.setName(data.getName());
            skill.setType(data.getType());
            skill.setValue(data.getValue());
            skill = skillRepository.save(skill);
        }
        return skill;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSkill(@PathVariable(value = "id") final Long id) {
        skillService.deleteSkill(skillRepository.findOne(id));
    }

    @GetMapping(value = "artefact/{artefact_id}")
    public List<Skill> getSkillsForArtefact(@PathVariable(value = "artefact_id") final Long id) {
        final Artefact a = artefactService.getArtefact(id);
        return skillService.getSkillsForArtefact(a);
    }

}
