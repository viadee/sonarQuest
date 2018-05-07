package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.dtos.SkillDto;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.SkillRepository;
import com.viadee.sonarQuest.services.ArtefactService;
import com.viadee.sonarQuest.services.SkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skill")
public class SkillController {
    
    private SkillRepository skillRepository;
    
    @Autowired
    private ArtefactService artefactService;
    
    @Autowired
    private SkillService skillService;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SkillDto> getAllSkills() {
        return this.skillRepository.findAll().stream().map(this::toSkillDto).collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SkillDto getSkillById(@PathVariable(value = "id") Long id) {
        Skill skill = this.skillRepository.findOne(id);
        return this.toSkillDto(skill);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(@RequestBody SkillDto skillDto) {
        return this.skillRepository.save(new Skill(skillDto.getName(),skillDto.getType(),skillDto.getValue()));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Skill updateSkill(@PathVariable(value = "id") Long id, @RequestBody SkillDto skillDto) {
        Skill skill = this.skillRepository.findOne(id);
        if (skill != null) {
            skill.setName(skillDto.getName());
            skill = this.skillRepository.save(skill);
        }
        return skill;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSkill(@PathVariable(value = "id") Long id) {
    	this.skillService.deleteSkill(this.skillRepository.findOne(id));
    }
    
    @CrossOrigin
    @RequestMapping(value = "artefact/{artefact_id}", method = RequestMethod.GET)
    public List<SkillDto> getSkillsForArtefact(@PathVariable(value = "artefact_id") Long id) {
    	ArtefactDto a = this.artefactService.getArtefact(id);
    	return this.skillService.getSkillsForArtefact(a);
    }
    
    
    private SkillDto toSkillDto(Skill skill) {
        SkillDto skillDto = null;
        if (skill != null) {
            skillDto = new SkillDto(skill.getId(), skill.getName(),skill.getType(),skill.getValue() ,skill.getAvatarClasses());
        }
        return skillDto;
    }
}
