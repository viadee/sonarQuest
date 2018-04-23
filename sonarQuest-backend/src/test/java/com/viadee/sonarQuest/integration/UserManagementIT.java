package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.services.DeveloperService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "simulateSonarServer=true")
public class UserManagementIT {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Test
    public void testCreateDeleteDeveloper() {
        // Given
        DeveloperDto developerDto = new DeveloperDto("testusername");
        final long count = developerRepository.count();

        // When
        developerService.createDeveloper(developerDto);

        // Then
        Developer dev = developerRepository.findByUsername("testusername");
        assertNotNull("developer could not be created", dev);
        assertEquals("number of devs is inconsistent", 4, developerRepository.count());
        assertFalse(dev.isDeleted());

        // When
        developerService.deleteDeveloper(dev);
        assertTrue("developer was not deleted", developerRepository.findByUsername("testusername").isDeleted());
        assertEquals("number of devs is inconsistent", developerRepository.count(), count + 1); // since deleting is
                                                                                                // logical
                                                                                                // deleting and not
                                                                                                // physical
        assertTrue(dev.isDeleted());

    }

    @Test
    public void testFilterOutDeletedDevelopers() {
        // Given
        java.util.List<Developer> before = developerService.findActiveDevelopers();

        // When
        Developer dev = developerRepository.findAll().get(0);
        developerService.deleteDeveloper(dev);
        java.util.List<Developer> after = developerService.findActiveDevelopers();

        // Then
        assertTrue("active developers should not contain deleted ones",
                after.size() == before.size() - 1);

    }

}