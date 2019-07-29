package com.viadee.sonarquest.skilltree.dto;

import java.sql.Timestamp;

public class SonarRuleDTO {

	private Long id;
	private String name;
	private String key;
	private Timestamp addedAt;
	private UserSkillDTO userSkilLDto;

	public SonarRuleDTO() {

	}

	public SonarRuleDTO(String name, String key) {
		super();
		this.name = name;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}

	public UserSkillDTO getUserSkilLDto() {
		return userSkilLDto;
	}

	public void setUserSkilLDto(UserSkillDTO userSkilLDto) {
		this.userSkilLDto = userSkilLDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
