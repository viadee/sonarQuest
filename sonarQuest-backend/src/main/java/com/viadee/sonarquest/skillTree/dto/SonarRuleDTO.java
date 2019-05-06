package com.viadee.sonarquest.skillTree.dto;

import java.sql.Timestamp;

public class SonarRuleDTO {

	private String name;
	private String key;
	private Timestamp addedAt;

	public SonarRuleDTO() {
		super();
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

}
