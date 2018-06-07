package com.viadee.sonarQuest.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssuePaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
                List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();
                SonarQubeIssueRessource sonarQubeIssueRessource=getSonarQubeIssueResourceForProjectAndPageIndex(projectKey,1);
                sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());
                Integer pagesOfExternalIssues = determinePagesOfExternalIssuesToBeRequested(sonarQubeIssueRessource.getPaging());
                for(int i = 2; i <= pagesOfExternalIssues; i++){
                    sonarQubeIssueList.addAll(getSonarQubeIssueResourceForProjectAndPageIndex(projectKey,i).getIssues());
                }
                issues = sonarQubeIssueList;
            } catch (final Exception e) {
                throw new RuntimeException("Could not load simulated sonar projects", e);
            }
        }
        return issues;
    }

    public int determinePagesOfExternalIssuesToBeRequested (SonarQubeIssuePaging sonarQubeIssuePaging){
        return sonarQubeIssuePaging.getTotal() / sonarQubeIssuePaging.getPageSize() + 1;
    }

    public SonarQubeIssueRessource getSonarQubeIssueResourceForProjectAndPageIndex(String projectKey, int pageIndex){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget= client.target(RessourceEndpoints.DEV_ENDPOINT + "issues/search?componentRoots=" + projectKey+ "&pageSize=500&pageIndex="+pageIndex);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        SonarQubeIssueRessource sonarQubeIssueRessource
                = invocationBuilder.get(SonarQubeIssueRessource.class);
        return  sonarQubeIssueRessource;
    }
}
