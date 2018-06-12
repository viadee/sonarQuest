package com.viadee.sonarQuest.services;

<<<<<<< HEAD
import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.externalRessources.*;
=======
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
>>>>>>> master
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;


@Service
@ConditionalOnProperty(value = "simulateSonarServer", havingValue = "false", matchIfMissing = true)
public class RealExternalRessourceService extends ExternalRessourceService {

    @Value("${resourceEndpoint}")
    private String resourceEndpoint;

    private static final Logger log = LoggerFactory.getLogger(RealExternalRessourceService.class);
    private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the sonar server or start this server with --simulateSonarServer=true";

    @Override public List<SonarQubeProject> getSonarQubeProjects() {
        try {
<<<<<<< HEAD
            List<SonarQubeProject> sonarQubeProjects = new ArrayList<>();
            SonarQubeProjectRessource sonarQubeProjectRessource = getSonarQubeProjecRessourceForPageIndex(1);
            sonarQubeProjects.addAll(sonarQubeProjectRessource.getSonarQubeProjects());
            Integer pagesOfExternalProjects = determinePagesOfExternalRessourcesToBeRequested(sonarQubeProjectRessource.getPaging());
            for(int i = 2; i <= pagesOfExternalProjects; i++) {
                sonarQubeProjects.addAll(getSonarQubeProjecRessourceForPageIndex(i).getSonarQubeProjects());
            }
            return sonarQubeProjects;
=======
            ResponseEntity<List<SonarQubeProject>> projectResponse = new RestTemplate().exchange(resourceEndpoint + "/projects", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<SonarQubeProject>>() {});
            return projectResponse.getBody();
>>>>>>> master
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException)
                log.error(ERROR_NO_CONNECTION);
            throw e;
        }
    }

    @Override public List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey) {
        try {
<<<<<<< HEAD
            List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();

            SonarQubeIssueRessource sonarQubeIssueRessource=getSonarQubeIssueResourceForProjectAndPageIndex(projectKey,1);

            sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());

            Integer pagesOfExternalIssues = determinePagesOfExternalRessourcesToBeRequested(sonarQubeIssueRessource.getPaging());

            for(int i = 2; i <= pagesOfExternalIssues; i++) {
                sonarQubeIssueList.addAll(getSonarQubeIssueResourceForProjectAndPageIndex(projectKey, i).getIssues());
            }
            return sonarQubeIssueList;

=======
            RestTemplate restTemplate = new RestTemplate();
            SonarQubeIssueRessource sonarQubeIssueRessource = restTemplate.getForObject(resourceEndpoint + "/issues/search?projectKeys=" + projectKey,
                    SonarQubeIssueRessource.class);
            return sonarQubeIssueRessource.getIssues();
>>>>>>> master
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof ConnectException)
                log.error(ERROR_NO_CONNECTION);
            throw e;
        }
    }

    public int determinePagesOfExternalRessourcesToBeRequested(SonarQubePaging sonarQubePaging){
        return sonarQubePaging.getTotal() / sonarQubePaging.getPageSize() + 1;
    }

    public SonarQubeIssueRessource getSonarQubeIssueResourceForProjectAndPageIndex(String projectKey, int pageIndex){
        Client client = ClientBuilder.newClient();

        WebTarget webTarget= client.target(RessourceEndpoints.DEV_ENDPOINT + "/issues/search?componentRoots=" + projectKey+ "&pageSize=500&pageIndex="+pageIndex);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        SonarQubeIssueRessource sonarQubeIssueRessource
                = invocationBuilder.get(SonarQubeIssueRessource.class);

        return  sonarQubeIssueRessource;
    }

    public SonarQubeProjectRessource getSonarQubeProjecRessourceForPageIndex(int pageIndex){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(RessourceEndpoints.DEV_ENDPOINT + "/components/search?qualifiers=TRK&pageSize=500&pageIndex="+pageIndex);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        SonarQubeProjectRessource sonarQubeProjectRessource = invocationBuilder.get(SonarQubeProjectRessource.class);
        return sonarQubeProjectRessource;
    }
}
