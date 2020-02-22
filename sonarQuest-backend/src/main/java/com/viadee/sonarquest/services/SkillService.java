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

    private final ArtefactService artefactService;

    public SkillService(SkillRepository skillRepository, ArtefactService artefactService) {
        this.skillRepository = skillRepository;
        this.artefactService = artefactService;
    }

    @Transactional
    public Skill createSkill(final Skill skillDto) {
        final Skill skill = new Skill();
        skill.setName(skillDto.getName());
        skill.setType(skillDto.getType());
        skill.setValue(skillDto.getValue());
        return skillRepository.save(skill);
    }

    @Transactional
    public List<Skill> getSkillsForArtefact(final Long artefactId) {
        return artefactService.getArtefact(artefactId).getSkills();
    }

    @Transactional
    public void deleteSkillById(final Long skillId) {
            skillRepository.deleteById(skillId);
    }

    @Transactional
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Transactional
    public Skill getSkillById(final Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public Skill updateSkill(final Long skillId, final Skill newSkill) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(ResourceNotFoundException::new);
        skill.setName(newSkill.getName());
        skill.setType(newSkill.getType());
        skill.setValue(newSkill.getValue());
        return skillRepository.save(skill);
    }
}
