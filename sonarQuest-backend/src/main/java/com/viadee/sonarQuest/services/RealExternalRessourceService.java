package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.List;

@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "false", matchIfMissing = true)
public class RealExternalRessourceService extends ExternalRessourceService {
    private static final Logger log = LoggerFactory.getLogger(RealExternalRessourceService.class);
    private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the sonar server or start this server with --simulateSonarServer=true";

    @Override public List<SonarQubeProject> getSonarQubeProjects() {
        try {
            ResponseEntity<List<SonarQubeProject>> projectResponse = new RestTemplate().exchange(RessourceEndpoints.DEV_ENDPOINT + "/projects", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<SonarQubeProject>>() {});
            return projectResponse.getBody();
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException)
                log.error(ERROR_NO_CONNECTION);
            throw e;
        }
    }

    @Override public List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            SonarQubeIssueRessource sonarQubeIssueRessource = restTemplate.getForObject(RessourceEndpoints.DEV_ENDPOINT + "/issues/search?projectKeys=" + projectKey,
                    SonarQubeIssueRessource.class);
            return sonarQubeIssueRessource.getIssues();
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException)
                log.error(ERROR_NO_CONNECTION);
            throw e;
        }
    }
}
