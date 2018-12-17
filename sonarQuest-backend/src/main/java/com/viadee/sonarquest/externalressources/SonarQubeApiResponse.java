package com.viadee.sonarquest.externalressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarQubeApiResponse {

    List<SonarQubeWebService> webServices;

    public SonarQubeApiResponse() {
    }


    public List<SonarQubeWebService> getWebServices() {
        return webServices;
    }

    public void setWebServices(List<SonarQubeWebService> webServices) {
        this.webServices = webServices;
    }
}

