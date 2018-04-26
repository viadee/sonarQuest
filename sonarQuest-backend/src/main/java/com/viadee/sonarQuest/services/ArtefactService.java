package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.LevelRepository;

@Service 
public class ArtefactService {
	
	@Autowired
	ArtefactRepository artefactRepository;

	@Autowired
	LevelRepository levelRepository;
	
	@Autowired
	DeveloperRepository developerRepository;
	
	@Autowired
	SkillService skillService;

	@Autowired
	DeveloperService developerService;
	

	public List<ArtefactDto> getArtefacts(){
 		return artefactRepository.findAll().stream().map(this::toArtefactDto).collect(Collectors.toList());
	}
	
	public List<ArtefactDto> getArtefactsforMarkteplace(){
		return artefactRepository.findByQuantityIsGreaterThanEqual((long)1).stream().map(this::toArtefactDto).collect(Collectors.toList());
	}
	

	public ArtefactDto getArtefact(long id){
		Artefact artefact = artefactRepository.findOne(id);
	    ArtefactDto artefactDto = null;
	    if(artefact != null){
	        artefactDto = toArtefactDto(artefact);
	    }
	    return artefactDto;
	}
	
	public ArtefactDto createArtefact(ArtefactDto artefactDto) {

		Artefact artefact = artefactRepository.save(new Artefact(artefactDto.getName(), artefactDto.getIcon(), artefactDto.getPrice(),artefactDto.getQuantity(), artefactDto.getDescription()));
		
		List<Artefact> artefacts = new ArrayList<>();
		artefacts.add(artefact);
		
	
		Level lvl = levelRepository.save(new Level(artefactDto.getMinLevel().getMin(),null,null,artefacts));
		
		Iterator<Skill> i = artefactDto.getSkills().iterator();
		Skill s;
		ArrayList<Skill> skills = new ArrayList<>();
		while (i.hasNext()){
			s = i.next();
			Skill skl = skillService.createSkill(s);
			skills.add(skl);
		}

		artefact.setMinLevel(lvl);
		artefact.setSkills(skills);

		artefactRepository.save(artefact);
        return toArtefactDto(artefact);
	}
	
	public ArtefactDto updateArtefact(Long id, ArtefactDto artefactDto) {
		
		Artefact artefact = artefactRepository.findOne(id);
		Level minLevel = levelRepository.findById(artefact.getMinLevel().getId());
		minLevel.setMin(artefactDto.getMinLevel().getMin());
		levelRepository.save(minLevel);


		artefact.setName(artefactDto.getName());
		artefact.setIcon(artefactDto.getIcon());
		artefact.setPrice(artefactDto.getPrice());
		artefact.setDescription(artefactDto.getDescription());
		artefact.setQuantity(artefactDto.getQuantity());
		artefact.setMinLevel(minLevel);
		artefact.setSkills(artefactDto.getSkills());
		artefact = artefactRepository.save(artefact);
		return toArtefactDto(artefact);
	}
	
	public ArtefactDto toArtefactDto(Artefact artefact) {
        ArtefactDto artefactDto=null;
        if(artefact!=null){
            artefactDto= new ArtefactDto(artefact.getId(),artefact.getName(),artefact.getIcon(),artefact.getPrice(), artefact.getQuantity(), artefact.getDescription() ,artefact.getMinLevel(),artefact.getSkills());
        }
        return artefactDto;
    }

	
	
	public ArtefactDto buyArtefact(Artefact artefact, Developer developer) {

		List<Artefact> developerArtefacts;
		developerArtefacts = developer.getArtefacts();
		
		
		//If developer has TOO LITTLE GOLD, Then the purchase is canceled 
		long gold = developer.getGold() - artefact.getPrice();
		if (gold < (long) 0) {
			return null;
		}
		
		
		// If the developer has ALREADY BOUGHT the Artefact, Then the purchase is canceled 
		for (Artefact a : developerArtefacts) {
			if (a.equals(artefact)) {
				return null;
			}
		}
		
		
		// If the artefact is SOLD OUT, then the purchase is canceled
		if (artefact.getQuantity() < 1) {
			return null;
		}
		
		
		// When the LEVEL of the developer is too low, then the purchase is canceled
		long minLevel = artefact.getMinLevel().getMin();
		long xp		  = developer.getXp();
		long devLevel = developerService.getLevel(xp);
		
		if (minLevel > devLevel) {
			return null;
		}
		
		
		developerArtefacts.add(artefact);
		developer.setArtefacts(developerArtefacts);
		developer.setGold(gold);
		developer = developerRepository.save(developer);
	
		artefact.setQuantity(artefact.getQuantity() - 1);
		artefact = artefactRepository.save(artefact);
		return toArtefactDto(artefact);
	}
	
	
	
	
}
