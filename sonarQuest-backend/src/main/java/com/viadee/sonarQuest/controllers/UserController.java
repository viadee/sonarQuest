package com.viadee.sonarQuest.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping(PathConstants.USER_URL)
public class UserController {

    @Value("${avatar.directory}")
    private String avatarDirectoryPath;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public User getUser(final Principal principal) {
        final String username = principal.getName();
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAuthority('FULL_USER_ACCESS')")
    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<User> users(final Principal principal) {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User updateUser(@RequestBody final User user) {
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('FULL_USER_ACCESS')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteUser(@PathVariable(value = "id") final Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

    @RequestMapping(path = "/avatar", method = RequestMethod.GET)
    public @ResponseBody byte[] avatar(final Principal principal, final HttpServletResponse response)
            throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=avatar.png");
        final User user = getUser(principal);
        return user != null ? loadAvatar(user) : null;
    }

    @RequestMapping(path = "/{id}/avatar", method = RequestMethod.GET)
    public @ResponseBody byte[] avatarForUser(final Principal principal,
            @PathVariable(value = "id") final Long id,
            final HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=avatar.png");
        final User user = userService.findById(id);
        return loadAvatar(user);
    }

    private byte[] loadAvatar(final User user) throws IOException {
        final File avatarDirectory = new File(avatarDirectoryPath);
        final String avatarFilePath = avatarDirectory.getAbsolutePath() + File.separator + user.getPicture();
        if (new File(avatarFilePath).exists()) {
            return Files.toByteArray(new File(avatarFilePath));
        } else {
            return null;
        }
    }
}
