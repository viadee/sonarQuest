package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.World;
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
    private LevelService levelService;

    @Autowired
	private DeveloperRepository developerRepository;
	
	public Developer createDeveloper(DeveloperDto developerDto) {
		Developer developer;
        if (checkIfUsernameNotExists(developerDto)) {
            Level level1 = levelRepository.findById((long) 1);
            developer = developerRepository.save(new Developer(
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
        Developer developer = developerRepository.findByUsername(developerDto.getUsername());
        return developer == null;
    }

	public void deleteDeveloper(Developer developer) {
	    if (developer != null) {	        
	    	developer.setDeleted();	     
	    	developerRepository.save(developer);
	    }		    
	}
	
	public List<Developer> findActiveDevelopers(){
		return developerRepository.findByDeleted(false);
	}

	public Developer setWorld(Developer d, World w) {
		d.setWorld(w);
		developerRepository.save(d);

		return d;
	}

	public DeveloperDto updateDeveloper(DeveloperDto developerDto) {
		Developer developer = developerRepository.findById(developerDto.getId());
		if (developer != null) {
            developer.setGold((developerDto.getGold()));
            developer.setXp(developerDto.getXp());
            developer.setLevel(levelService.getLevelByDeveloperXp(developer.getXp()));
            developer.setPicture(developerDto.getPicture());
            developer.setAboutMe(developerDto.getAboutMe());
            developer.setAvatarClass(developerDto.getAvatarClass());
            developer.setAvatarRace(developerDto.getAvatarRace());
            developer.setArtefacts(developerDto.getArtefacts());
            developer = developerRepository.save(developer);
        }
        return toDeveloperDto(developer);
	}
	
	/**
     * Convert Developer into DeveloperDTO
     *
     * @param developer
     * @return
     */
    public DeveloperDto toDeveloperDto(Developer developer) {
        DeveloperDto developerDto = null;
        if(developer != null){
            developerDto = new DeveloperDto(developer.getId(), developer.getUsername(), developer.getGold(), developer.getXp(), developer.getLevel(),developer.getPicture(),developer.getAboutMe(), developer.getAvatarClass(),developer.getAvatarRace(),developer.getArtefacts(),developer.getAdventures(),developer.getParticipations(),developer.getWorld());
        }
        return developerDto;
    }

    
    
	public long getLevel(long xp) {
		return calculateLevel(xp,1);
	}
	

	private long calculateLevel(long xp, long level) {
		long step            = 10;
		long xpForNextLevel  = 0;
		    
		for (long i = 1; i <= level; i++) {
			xpForNextLevel = xpForNextLevel + step;
		}

		//Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
		if(level == 200 || (xp < xpForNextLevel)) {
	      return level;
	    } else {
		    return calculateLevel(xp, level+1);
		}
	}


}
