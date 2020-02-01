package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *	Represent the SonarQubeProjectStatus resource
 *	@see https://sonarcloud.io/web_api/api/qualitygates/project_status
 *
 */
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
