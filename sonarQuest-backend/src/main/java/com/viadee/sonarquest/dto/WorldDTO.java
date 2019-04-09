package com.viadee.sonarquest.dto;

public class WorldDTO {

	private Long id;

	private String name;

	private String project;

	private String image;

	private Boolean active;

	private Boolean usequestcards;

	public WorldDTO() {
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getUsequestcards() {
		return usequestcards;
	}

	public void setUsequestcards(Boolean usequestcards) {
		this.usequestcards = usequestcards;
	}

}
