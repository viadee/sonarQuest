package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Skill;

import java.util.List;

public class ArtefactDto {


    private Long id;

    private String name;

    private String icon;

    private Long price;

    private Long quantity;
    
    private String description;

    private Level minLevel;

    private List<Skill> skills;



    public ArtefactDto() {
    }

    

	public ArtefactDto(Long id, String name, String icon, Long price, Long quantity, String description, Level minLevel, List<Skill> skills) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.minLevel = minLevel;
		this.skills = skills;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Level getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
