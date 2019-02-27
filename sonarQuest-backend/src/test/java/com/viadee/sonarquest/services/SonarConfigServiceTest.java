package com.viadee.sonarquest.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.entities.SonarConfig;

@RunWith(SpringRunner.class)
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
