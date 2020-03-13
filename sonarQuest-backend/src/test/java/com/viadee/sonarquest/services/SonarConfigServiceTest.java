package com.viadee.sonarquest.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.viadee.sonarquest.entities.SonarConfig;

@SpringBootTest
public class SonarConfigServiceTest {

    @Autowired
    private SonarConfigService sonarConfigService;

    @Test
    public void testSonarQubeServerURLCheck() {
        final SonarConfig sonarConfig = new SonarConfig();
        sonarConfig.setSonarServerUrl("https://www.sonarcloud.io");
        assertTrue(sonarConfigService.checkSonarQubeURL(sonarConfig));

        sonarConfig.setSonarServerUrl("https://www.sonarcsadloud.io");
        assertFalse(sonarConfigService.checkSonarQubeURL(sonarConfig));
    }
}
