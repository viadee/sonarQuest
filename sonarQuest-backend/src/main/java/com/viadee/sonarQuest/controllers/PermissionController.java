package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Permission;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.services.PermissionService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public Set<Permission> getPermissions(final Principal principal) {
		final String username = principal.getName();
		final User user = userService.findByUsername(username);
		return user == null ? Collections.emptySet() : permissionService.getUrlPermissions(user);
	}
}
