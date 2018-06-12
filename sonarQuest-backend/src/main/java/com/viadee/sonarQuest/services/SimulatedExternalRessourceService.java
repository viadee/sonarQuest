package com.viadee.sonarQuest.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.SonarQubePaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.exception.BackendServiceRuntimeException;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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

    @Override
    public List<SonarQubeProject> getSonarQubeProjects() {
        initSonarConfigData();
        return sonarConfigService.getAll().stream()
                .map(config -> new SonarQubeProject(config.getSonarProject(), config.getName()))
                .collect(Collectors.toList());
    }

    public void initSonarConfigData() {
        final SonarConfig config = new SonarConfig();
        config.setName("World of Dragons");
        config.setSonarProject("org.apache.commons%3Acommons-lang3");
        config.setSonarServerUrl("https://sonarcloud.io");
        sonarConfigService.saveConfig(config);
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
