package com.viadee.sonarquest.externalressources;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeRule {

	private String key;
	private String name;
	private String repo;
	private Timestamp createdAt;
	private String lang;

	public SonarQubeRule() {
	}

	public SonarQubeRule(String key, String name, String repo, Timestamp createdAt, String lang) {
		this.key = key;
		this.name = name;
		this.repo = repo;
		this.createdAt = createdAt;
		this.lang = lang;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
