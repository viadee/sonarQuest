package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarQuest.entities.Role;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagementIT {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        // Given
        final Role role = new Role();
        role.setId(1L);
        final User user = new User();
        user.setUsername("dev");
        user.setPassword("test");
        user.setRole(role);
        // Then
        final User dev = userService.findByUsername("dev");
        assertNotNull("dev could not be created", dev);
    }

}