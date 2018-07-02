package com.viadee.sonarQuest.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarQuest.exception.BackendServiceRuntimeException;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.externalRessources.SonarQubeProjectRessource;

/**
 * Reads data for external requests from locally saved json files
 */
@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "true")
public class SimulatedExternalRessourceService extends ExternalRessourceService {

    @Autowired
    public ObjectMapper mapper;

    private List<SonarQubeIssue> issues = null;

    private List<SonarQubeProject> projects = null;

    @Override
    public List<SonarQubeProject> getSonarQubeProjects() {
        if (projects == null) {
            try {
                projects = mapper
                        .readValue(SimulatedExternalRessourceService.class
                                .getResourceAsStream("/projectRessourceBackup.json"),
                                SonarQubeProjectRessource.class)
                        .getSonarQubeProjects();
            } catch (final IOException e) {
                throw new BackendServiceRuntimeException("Could not load simulated sonar projects", e);
            }
        }
        return projects;
    }

    @Override
    public List<SonarQubeIssue> getIssuesForSonarQubeProject(final String projectKey) {
        if (issues == null) {
            try {
                issues = mapper
                        .readValue(SimulatedExternalRessourceService.class.getResourceAsStream("/issueRessource.json"),
                                SonarQubeIssueRessource.class)
                        .getIssues();
            } catch (final IOException e) {
                throw new BackendServiceRuntimeException("Could not load simulated sonar projects", e);
            }
        }
        return issues;
    }

}
