package com.viadee.sonarQuest.services;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.dtos.SkillDto;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import com.viadee.sonarQuest.repositories.SkillRepository;

@Service
public class SkillService {
	

    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ArtefactRepository artefactRepository;
	
	public Skill createSkill(Skill skillDto) {
		Skill skill = new Skill();
		skill.setName(skillDto.getName());
		skill.setType(skillDto.getType());
		skill.setValue(skillDto.getValue());
		return this.skillRepository.save(skill);
	}
	
	public List<SkillDto> getSkillsForArtefact(ArtefactDto a) {
		List<Skill> skills = this.artefactRepository.findOne(a.getId()).getSkills();
		return this.toSkillsDto(skills);
	}

	
	
	public static SkillDto toSkillDto(Skill skill) {
		SkillDto skillDto = null;
        if (skill != null) {
        	skillDto = new SkillDto(skill.getId(), skill.getName(), skill.getType(), skill.getValue(), skill.getAvatarClasses());
        }
        return skillDto;
    }
	
	
    public List<SkillDto> toSkillsDto(List<Skill> skills) {
    	List<SkillDto> skillDto = new ArrayList<>();
    
        	Iterator<Skill> it = skills.iterator();
        	while(it.hasNext()) {
        		Skill skill = it.next();
        		skillDto.add(toSkillDto(skill));
        	}
        return skillDto;
    }

	public void deleteSkill(Skill skill) {
		if (skill != null) {
            this.skillRepository.delete(skill);
        }
	}
	
}
