package com.viadee.sonarquest.externalressources;


import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.services.RestTemplateService;
import com.viadee.sonarquest.services.SonarConfigService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled
public class SonarQubeApiCallIT {

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private SonarConfigService sonarConfigService;

    @Test
    public void searchComponents() {
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

}
