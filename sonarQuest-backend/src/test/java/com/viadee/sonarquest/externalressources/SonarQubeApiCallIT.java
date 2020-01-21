package com.viadee.sonarquest.externalressources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.externalressources.SonarQubeApiCall;
import com.viadee.sonarquest.externalressources.SonarQubeComponentQualifier;
import com.viadee.sonarquest.externalressources.SonarQubeProjectRessource;
import com.viadee.sonarquest.services.RestTemplateService;
import com.viadee.sonarquest.services.SonarConfigService;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class SonarQubeApiCallIT {

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private SonarConfigService sonarConfigService;

    @Test
    public void searchComponents() throws Exception {
        SonarConfig sonarConfig = sonarConfigService.getConfig();
        String sonarQubeServerUrl = sonarConfig.getSonarServerUrl();
        String projectKey = "assertj";
        SonarQubeApiCall apiCall = SonarQubeApiCall
                .onServer(sonarQubeServerUrl)
                .searchComponents(SonarQubeComponentQualifier.TRK)
                .withQuery(projectKey)
                .build();
        RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);

        final ResponseEntity<SonarQubeProjectRessource> response = restTemplate.getForEntity(
                apiCall.asString(),
                SonarQubeProjectRessource.class);
        assertEquals(1, response.getBody().getSonarQubeProjects().size());
    }
    
    @Test
    public void projectStatus() {
    	SonarConfig sonarConfig = sonarConfigService.getConfig();
    	String sonarQubeServerUrl = sonarConfig.getSonarServerUrl();
        String projectKey = "org.synyx:urlaubsverwaltung";
        SonarQubeApiCall apiCall = SonarQubeApiCall
                .onServer(sonarQubeServerUrl)
                .projectStatus()
                .withProjectKey(projectKey)
                .build();
        RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
        
        final ResponseEntity<SonarQubeProjectStatusRessource> response = restTemplate.getForEntity(
                apiCall.asString(),
                SonarQubeProjectStatusRessource.class);
        
        assertNotNull(response.getBody().getProjectStatus());
    }

}
