package com.viadee.sonarquest.externalressources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.services.RestTemplateService;
import com.viadee.sonarquest.services.SonarConfigService;

@SpringBootTest
@Disabled
public class SonarQubeApiCallIT {

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private SonarConfigService sonarConfigService;

    @Test
    public void searchComponents() {
        final SonarConfig sonarConfig = sonarConfigService.getConfig();
        final String sonarQubeServerUrl = sonarConfig.getSonarServerUrl();
        final String projectKey = "assertj";
        final SonarQubeApiCall apiCall = SonarQubeApiCall
                .onServer(sonarQubeServerUrl)
                .searchComponents(SonarQubeComponentQualifier.TRK)
                .withQuery(projectKey)
                .build();
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);

        final ResponseEntity<SonarQubeProjectRessource> response = restTemplate.getForEntity(
                apiCall.asString(),
                SonarQubeProjectRessource.class);
        assertEquals(1, response.getBody().getSonarQubeProjects().size());
    }

}
