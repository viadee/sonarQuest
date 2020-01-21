package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeProjectStatusRessource {

	@JsonProperty("projectStatus")
	private SonarQubeProjectStatus projectStatus;

	public SonarQubeProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(SonarQubeProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}
}
