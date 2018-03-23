package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "false", matchIfMissing = true)
public class RealExternalRessourceService extends ExternalRessourceService{
    @Override public List<SonarQubeProject> getSonarQubeProjects(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<SonarQubeProject>> projectResponse = restTemplate.exchange(RessourceEndpoints.DEV_ENDPOINT+"/projects", HttpMethod.GET, null, new ParameterizedTypeReference<List<SonarQubeProject>>() {});
        return projectResponse.getBody();
    }

    @Override public List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey){
        RestTemplate restTemplate = new RestTemplate();
        SonarQubeIssueRessource sonarQubeIssueRessource = restTemplate.getForObject(RessourceEndpoints.DEV_ENDPOINT+"/issues/search?projectKeys=" +projectKey, SonarQubeIssueRessource.class);
        return sonarQubeIssueRessource.getIssues();
    }
}
