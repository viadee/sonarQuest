package com.viadee.sonarquest.integration;


import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(SpringExtension.class)
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