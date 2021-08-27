package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testAvatar_userServiceDoesntFindUser() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Aria");
        when(userService.findByUsername("Aria")).thenReturn(null);
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] avatar = userController.avatar(principal, response);
        assertNull(avatar);
    }

    @Test
    public void testAvatar_userServiceWithUserWithoutAvatar() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Aria");
        User user = mock(User.class);
        when(user.getPicture()).thenReturn(null);
        when(userService.findByUsername("Aria")).thenReturn(user);
        ReflectionTestUtils.setField(userController, "avatarDirectoryPath",
                "avatar");
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] avatar = userController.avatar(principal, response);
        assertTrue(avatar.length == 0);
    }

}
