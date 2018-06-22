package com.viadee.sonarQuest.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.exception.BackendServiceRuntimeException;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;

/**
 * Reads data for external requests from locally saved json files
 */
@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "true")
public class SimulatedExternalRessourceService extends ExternalRessourceService {

    @Autowired
    public ObjectMapper mapper;

    private List<SonarQubeIssue> issues = null;

    @Autowired
    private SonarConfigService sonarConfigService;

    @Autowired
    private WorldService worldService;

    @Override
    public List<SonarQubeProject> getSonarQubeProjects() {

        return sonarConfigService.getAll().stream()
                .map(config -> new SonarQubeProject(config.getSonarProject(), config.getName()))
                .collect(Collectors.toList());
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
