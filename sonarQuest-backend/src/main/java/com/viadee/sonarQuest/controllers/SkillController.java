package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.SkillDto;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.SkillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skill")
public class SkillController {
    
    private SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SkillDto> getAllSkills() {
        return this.skillRepository.findAll().stream().map(skill -> toSkillDto(skill)).collect(Collectors.toList());
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSkill(@PathVariable(value = "id") Long id) {
        Skill skill = this.skillRepository.findOne(id);
        if (skill != null) {
            this.skillRepository.delete(skill);
        }
    }
    
    private SkillDto toSkillDto(Skill skill) {
        SkillDto skillDto = null;
        if (skill != null) {
            skillDto = new SkillDto(skill.getId(), skill.getName(),skill.getType(),skill.getValue() ,skill.getAvatarClasses());
        }
        return skillDto;
    }
}
