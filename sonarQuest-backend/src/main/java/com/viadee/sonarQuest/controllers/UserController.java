package com.viadee.sonarQuest.controllers;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;
import com.viadee.sonarQuest.SonarQuestApplication;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping(PathConstants.USER_URL)
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public User user(final Principal principal) {
        final String username = principal.getName();
        return userService.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/developer")
    public List<User> developers(final Principal principal) {
        return userService.findByRole(RoleName.DEVELOPER);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<User> users(final Principal principal) {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User updateUser(@RequestBody final User user) {
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public HttpStatus deleteUser(@RequestBody final User user) {
        userService.delete(user);
        return HttpStatus.OK;
    }

    @RequestMapping(path = "/avatar", method = RequestMethod.GET)
    public @ResponseBody byte[] avatar(final Principal principal, final HttpServletResponse response)
            throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=avatar.png");

        final User user = user(principal);
        String path;
        final String propertiesFilePath = "client.properties";
        File avatarPath = new File(propertiesFilePath);

        if (!avatarPath.exists()) {
            try {
                final CodeSource codeSource = SonarQuestApplication.class.getProtectionDomain().getCodeSource();
                final File jarFile = new File(codeSource.getLocation().toURI().getPath());
                final String jarDir = jarFile.getParentFile().getPath();
                avatarPath = new File(jarDir + System.getProperty("file.separator") + propertiesFilePath);
                avatarPath = new File(avatarPath.getParentFile().getParentFile().getParentFile() + "/avatar");
            } catch (final Exception ignored) {
                LOG.error("Exception when trying to read the avatars!", ignored);
            }
        }

        final File folder = new File(avatarPath.getAbsolutePath());
        path = folder + "/" + user.getPicture();

        if (new File(path).isFile()) {
            return Files.toByteArray(new File(path));
        } else {
            return null;
        }

    }
}
