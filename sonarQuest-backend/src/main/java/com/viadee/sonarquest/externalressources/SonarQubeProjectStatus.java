package com.viadee.sonarquest.externalressources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *	Represent the SonarQubeProjectStatus
 *	@see https://sonarcloud.io/web_api/api/qualitygates/project_status 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeProjectStatus {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("conditions")
	private List<SonarQubeCondition> conditions;

	public List<SonarQubeCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<SonarQubeCondition> conditions) {
		this.conditions = conditions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
