package com.viadee.sonarQuest.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Reads data for external requests from locally saved json files
 */
@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "true")
public class SimulatedExternalRessourceService extends ExternalRessourceService{
    @Autowired
    public ObjectMapper mapper;

    private List<SonarQubeIssue> issues = null;

    @Override public List<SonarQubeProject> getSonarQubeProjects(){
        try {
           return mapper.readValue(SimulatedExternalRessourceService.class.getResourceAsStream("/projectRessource.json"),new TypeReference<List<SonarQubeProject>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Could not load simulated sonar projects",e);
        }
    }

    @Override public List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey){
        if(issues == null) {
            try {
                issues = mapper.readValue(SimulatedExternalRessourceService.class.getResourceAsStream("/issueRessource.json"), SonarQubeIssueRessource.class).getIssues();
            } catch (IOException e) {
                throw new RuntimeException("Could not load simulated sonar projects",e);
            }
        }
        return issues;
    }
}
