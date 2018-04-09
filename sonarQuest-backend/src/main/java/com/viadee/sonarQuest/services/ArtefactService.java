package com.viadee.sonarQuest.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.dtos.SkillDto;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import com.viadee.sonarQuest.repositories.LevelRepository;
import com.viadee.sonarQuest.repositories.SkillRepository;

@Service 
public class ArtefactService {
	
	@Autowired
	ArtefactRepository artefactRepository;
	
	@Autowired
	LevelRepository levelRepository;
	
	@Autowired
	SkillService skillService;
	

	public List<ArtefactDto> getArtefacts(){
 		return this.artefactRepository.findAll().stream().map(artefact -> toArtefactDto(artefact)).collect(Collectors.toList());
	}
	

	public ArtefactDto getArtefact(long id){
		Artefact artefact = this.artefactRepository.findOne(id);
	    ArtefactDto artefactDto = null;
	    if(artefact != null){
	        artefactDto = this.toArtefactDto(artefact);
	    }
	    return artefactDto;
	}
	
	public ArtefactDto createArtefact(ArtefactDto artefactDto) {

		Artefact artefact = this.artefactRepository.save(new Artefact(artefactDto.getName(),artefactDto.getPrice(),artefactDto.getQuantity(), artefactDto.getDescription()));
		
		List<Artefact> artefacts = new ArrayList<Artefact>();
		artefacts.add(artefact);
		
	
		Level lvl = this.levelRepository.save(new Level(artefactDto.getMinLevel().getMin(),null,null,artefacts));
		
		Iterator<Skill> i = artefactDto.getSkills().iterator();
		Skill s;
		ArrayList<Skill> skills = new ArrayList<Skill>();
		while (i.hasNext()){
			s = i.next();
			Skill skl = this.skillService.createSkill(s);
			skills.add(skl);
		}

		artefact.setMinLevel(lvl);
		artefact.setSkills(skills);		
		
		this.artefactRepository.save(artefact);
        return this.toArtefactDto(artefact);
	}
	
	public ArtefactDto updateArtefact(Long id, ArtefactDto artefactDto) {
		
		Artefact artefact = this.artefactRepository.findOne(id);
		Level minLevel = this.levelRepository.findById(artefact.getMinLevel().getId());
		minLevel.setMin(artefactDto.getMinLevel().getMin());
		this.levelRepository.save(minLevel);
			
		
        if (artefact != null) {
            artefact.setName(artefactDto.getName());
            artefact.setIcon(artefactDto.getIcon());
            artefact.setPrice(artefactDto.getPrice());
            artefact.setDescription(artefactDto.getDescription());
            artefact.setQuantity(artefactDto.getQuantity());
            artefact.setMinLevel(minLevel);
            artefact.setSkills(artefactDto.getSkills());
            artefact = this.artefactRepository.save(artefact);
        }
        return this.toArtefactDto(artefact);
	}
	
	public ArtefactDto toArtefactDto(Artefact artefact) {
        ArtefactDto artefactDto=null;
        if(artefact!=null){
            artefactDto= new ArtefactDto(artefact.getId(),artefact.getName(),artefact.getIcon(),artefact.getPrice(), artefact.getQuantity(), artefact.getDescription() ,artefact.getMinLevel(),artefact.getSkills());
        }
        return artefactDto;
    }
	
	
	
	
}
