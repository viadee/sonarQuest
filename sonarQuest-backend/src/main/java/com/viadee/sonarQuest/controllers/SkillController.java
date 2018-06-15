package com.viadee.sonarQuest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.SkillRepository;
import com.viadee.sonarQuest.services.ArtefactService;
import com.viadee.sonarQuest.services.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

    private SkillRepository skillRepository;

    @Autowired
    private ArtefactService artefactService;

    @Autowired
    private SkillService skillService;

    public SkillController(final SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Skill> getAllSkills() {
        return this.skillRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Skill getSkillById(@PathVariable(value = "id") final Long id) {
        return this.skillRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(@RequestBody final Skill skillDto) {
        return this.skillRepository.save(skillDto);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Skill updateSkill(@PathVariable(value = "id") final Long id, @RequestBody final Skill data) {
        Skill skill = this.skillRepository.findOne(id);
        if (skill != null) {
            skill.setName(data.getName());
            skill = this.skillRepository.save(skill);
        }
        return skill;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSkill(@PathVariable(value = "id") final Long id) {
        this.skillService.deleteSkill(this.skillRepository.findOne(id));
    }

    @RequestMapping(value = "artefact/{artefact_id}", method = RequestMethod.GET)
    public List<Skill> getSkillsForArtefact(@PathVariable(value = "artefact_id") final Long id) {
        final Artefact a = this.artefactService.getArtefact(id);
        return this.skillService.getSkillsForArtefact(a);
    }

}
