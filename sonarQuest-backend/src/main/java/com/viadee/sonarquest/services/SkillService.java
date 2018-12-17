package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Skill;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import com.viadee.sonarquest.repositories.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ArtefactRepository artefactRepository;

    public Skill createSkill(final Skill skillDto) {
        final Skill skill = new Skill();
        skill.setName(skillDto.getName());
        skill.setType(skillDto.getType());
        skill.setValue(skillDto.getValue());
        return skillRepository.save(skill);
    }

    public List<Skill> getSkillsForArtefact(final Artefact a) {
        return artefactRepository.findOne(a.getId()).getSkills();
    }

    public void deleteSkill(final Skill skill) {
        if (skill != null) {
            skillRepository.delete(skill);
        }
    }

}
