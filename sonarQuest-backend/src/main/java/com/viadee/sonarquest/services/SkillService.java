package com.viadee.sonarquest.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Skill;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import com.viadee.sonarquest.repositories.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    private final ArtefactRepository artefactRepository;

    public SkillService(SkillRepository skillRepository, ArtefactRepository artefactRepository) {
        this.skillRepository = skillRepository;
        this.artefactRepository = artefactRepository;
    }

    @Transactional
    public Skill createSkill(final Skill skillDto) {
        final Skill skill = new Skill();
        skill.setName(skillDto.getName());
        skill.setType(skillDto.getType());
        skill.setValue(skillDto.getValue());
        return skillRepository.save(skill);
    }

    public List<Skill> getSkillsForArtefact(final Artefact a) {
        return artefactRepository.findById(a.getId()).orElseThrow(ResourceNotFoundException::new).getSkills();
    }

    @Transactional
    public void deleteSkill(final Skill skill) {
        if (skill != null) {
            skillRepository.delete(skill);
        }
    }

}
