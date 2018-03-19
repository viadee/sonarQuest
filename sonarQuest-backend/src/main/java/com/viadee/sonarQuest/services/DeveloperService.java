package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.LevelRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {

    @Autowired
	private LevelRepository levelRepository;

    @Autowired
	private DeveloperRepository developerRepository;
	
	public Developer createDeveloper(DeveloperDto developerDto) {
		Developer developer;
        if (this.checkIfUsernameNotExists(developerDto)) {
            Level level1 = this.levelRepository.findById((long) 1);
            developer = this.developerRepository.save(new Developer(
            		developerDto.getUsername(), 
            		(long) 0, 
            		(long) 0, 
            		level1, 
            		developerDto.getPicture(),
            		developerDto.getAboutMe(),
            		developerDto.getAvatarClass(),
            		developerDto.getAvatarRace(),
            		developerDto.getArtefacts(),
            		developerDto.getAdventures(),
            		developerDto.getParticipations()
            		));
        } else {
            developer = null;
        }
        return developer;
	}
	
	private Boolean checkIfUsernameNotExists(DeveloperDto developerDto) {
        Developer developer = this.developerRepository.findByUsername(developerDto.getUsername());
        return developer == null;
    }

	public void deleteDeveloper(Developer developer) {
	    if (developer != null) {	        
	    	developer.setDeleted();	     
	    	developerRepository.save(developer);
	    }		    
	}
	
	public List<Developer> findActiveDevelopers(){
		return this.developerRepository.findByDeleted(false);
	}

}
