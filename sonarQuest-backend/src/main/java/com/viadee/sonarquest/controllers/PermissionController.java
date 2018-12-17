package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Permission;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.PermissionService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserService userService;

	@GetMapping
	public Set<Permission> getPermissions(final Principal principal) {
		final String username = principal.getName();
		final User user = userService.findByUsername(username);
		return user == null ? Collections.emptySet() : permissionService.getUrlPermissions(user);
	}
}
