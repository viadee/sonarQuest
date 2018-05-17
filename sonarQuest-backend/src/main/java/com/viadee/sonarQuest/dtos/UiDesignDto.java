package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Developer;

public class UiDesignDto {
	
	private Long id;

    private String name;

    private Developer developer;

	public UiDesignDto() {
		super();
	}

	public UiDesignDto(Long id, String name, Developer developer) {
		super();
		this.id = id;
		this.name = name;
		this.developer = developer;
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

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
    
    

}
