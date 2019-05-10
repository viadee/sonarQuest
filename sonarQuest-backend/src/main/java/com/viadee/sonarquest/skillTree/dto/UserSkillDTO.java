package com.viadee.sonarquest.skillTree.dto;

import java.util.List;

public class UserSkillDTO {

	private String description;
	private String name;
	private Long id;
	private List<String> ruleKey;
	private Double score;
	private boolean isRoot;

	public UserSkillDTO() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(List<String> ruleKey) {
		this.ruleKey = ruleKey;
	}

	public void addRuleKey(String ruleKey) {
		this.ruleKey.add(ruleKey);
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	
}
