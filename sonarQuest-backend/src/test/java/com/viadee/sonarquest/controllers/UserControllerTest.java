package com.viadee.sonarquest.controllers;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testAvatar_userServiceFindetUserNicht() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Aria");
        when(userService.findByUsername("Aria")).thenReturn(null);
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] avatar = userController.avatar(principal, response);
        assertNull(avatar);
    }

    @Test
    public void testAvatar_userServiceMitUserOhnePicture() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Aria");
        User user = mock(User.class);
        when(user.getPicture()).thenReturn(null);
        when(userService.findByUsername("Aria")).thenReturn(user);
        ReflectionTestUtils.setField(userController, "avatarDirectoryPath",
                "avatar");
        HttpServletResponse response = mock(HttpServletResponse.class);
        byte[] avatar = userController.avatar(principal, response);
        assertNull(avatar);
    }

}
