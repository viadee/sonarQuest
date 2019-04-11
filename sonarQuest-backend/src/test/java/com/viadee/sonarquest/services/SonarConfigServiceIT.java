package com.viadee.sonarquest.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.entities.SonarConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarConfigServiceIT {

    @Autowired
    private SonarConfigService sonarConfigService;
    
    private SonarConfig sonarConfig;
    
    @Before
    public void setup() {
    	sonarConfig = new SonarConfig();	
	}

    @Test
    public void testSonarQubeServerURLCheck_urlIsReachable() {
    	//Given
        sonarConfig.setSonarServerUrl("https://www.sonarcloud.io");
        //When
        boolean urlReachable = sonarConfigService.checkSonarQubeURL(sonarConfig);
        //Then
		assertTrue(urlReachable);
    }
    
    @Test
    public void testSonarQubeServerURLCheck_urlIsNotReachable() {
    	//Given
        sonarConfig.setSonarServerUrl("https://www.sonarcsadloud.io");
        //When
        boolean urlReachable = sonarConfigService.checkSonarQubeURL(sonarConfig);
        //Then
		assertFalse(urlReachable);
    }
}
