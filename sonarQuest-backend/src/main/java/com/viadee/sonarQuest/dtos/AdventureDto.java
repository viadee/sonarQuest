package com.viadee.sonarQuest.dtos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;

public class AdventureDto {


    private Long id;

    private String title;

    private String story;

    private String status;

    private Long gold;

    private Long xp;
    
    private World world;

    private List<Quest> quests;

    private List<Developer> developers;

    public AdventureDto() {
    }
    

    public AdventureDto(Long id, String title, String story, String status, Long gold, Long xp, World world,
			List<Quest> quests, List<Developer> developers) {
		super();
		this.id = id;
		this.title = title;
		this.story = story;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
		this.quests = quests;
		this.developers = developers;
	}

	public Long getId() {
        return id;
    }

	public void setId(Long id) {
        this.id = id;
    }
	
    public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public static AdventureDto toAdventureDto(Adventure adventure) {
        AdventureDto adventureDto = null;
        if (adventure != null) {
            adventureDto = new AdventureDto(adventure.getId(), adventure.getTitle(), adventure.getStory(), adventure.getStatus(), adventure.getGold(), adventure.getXp(), adventure.getWorld(), adventure.getQuests(), adventure.getDevelopers());
        }
        return adventureDto;
    }
    
    /**
     * Converts a list of adventures to a list of adventuresDto
     * @param adventures
     * @return adventuresDto
     */
    public static List<AdventureDto> toAdventuresDto(List<Adventure> adventures) {
    	final List<AdventureDto> adventuresDto = new ArrayList<>();

		for (final Adventure adventure : adventures) {
			adventuresDto.add(toAdventureDto(adventure));
		}
        
        return adventuresDto;
    }
}
