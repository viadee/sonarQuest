package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.SonarQuestApplication;
import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.externalRessources.SonarQubeApiResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarConfigServiceTest {


    @Autowired
    SonarConfigService sonarConfigService;

    @Test
    public void testSonarQubeServerURLCheck(){
        SonarConfig sonarConfig = new SonarConfig();
        sonarConfig.setSonarServerUrl("https://www.sonarcloud.io");
        assertTrue(sonarConfigService.checkSonarQubeURL(sonarConfig));

        sonarConfig.setSonarServerUrl("https://www.sonarcsadloud.io");
        assertFalse(sonarConfigService.checkSonarQubeURL(sonarConfig));
    }
}
