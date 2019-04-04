package com.viadee.sonarquest.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping(PathConstants.USER_URL)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String DEFAULT_AVATAR_FILE = "avatar.png";

    private static final String HEADER_AVATAR_VALUE = "attachment; filename=" + DEFAULT_AVATAR_FILE;

    private static final String HEADER_AVATAR_NAME = "Content-Disposition";

    @Value("${avatar.directory}")
    private String avatarDirectoryPath;

    @Autowired
    private UserService userService;

    @GetMapping
    public User getUser(final Principal principal) {
    	User user = null;
    	if(principal != null) {
	        final String username = principal.getName();
	        user =  userService.findByUsername(username);
    	} 
    	return user;
    }

    @PreAuthorize("hasAuthority('FULL_USER_ACCESS')")
    @GetMapping(path = "/all")
    public List<User> users(final Principal principal) {
        return userService.findAll();
    }

    @PostMapping
    public User updateUser(@RequestBody final User data) {
        return userService.save(data);
    }

    @PreAuthorize("hasAuthority('FULL_USER_ACCESS')")
    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteUser(@PathVariable(value = "id") final Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping(path = "/avatar")
    public @ResponseBody byte[] avatar(final Principal principal, final HttpServletResponse response) {
        response.addHeader(HEADER_AVATAR_NAME, HEADER_AVATAR_VALUE);
        final User user = getUser(principal);
        return user != null ? loadAvatar(avatarDirectoryPath, user.getPicture()) : null;
    }

    @GetMapping(path = "/{id}/avatar")
    public @ResponseBody byte[] avatarForUser(final Principal principal, @PathVariable(value = "id") final Long id, final HttpServletResponse response) 
    		throws IOException {
        response.addHeader(HEADER_AVATAR_NAME, HEADER_AVATAR_VALUE);
        final User user = userService.findById(id);
        return loadAvatar(avatarDirectoryPath, user.getPicture());
    }

    public byte[] loadAvatar(String avaDir, final String avatarPic) {
        if (avatarPic != null) {
            String avatarFileName = avaDir + avatarPic;
            InputStream inputStream = SpringApplication.class.getResourceAsStream(avatarFileName);
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException ex) {
                LOGGER.error("Avatar file not found: " + avatarFileName, ex);
            }
        }
        return null;
    }

	public String getAvatarDirectoryPath() {
		return avatarDirectoryPath;
	}
}
