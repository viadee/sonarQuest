package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.Permission;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.PermissionService;
import com.viadee.sonarquest.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	private final PermissionService permissionService;

	private final UserService userService;

	public PermissionController(PermissionService permissionService, UserService userService) {
		this.permissionService = permissionService;
		this.userService = userService;
	}

	@GetMapping
	public Set<Permission> getPermissions(final Principal principal) {
		final String username = principal.getName();
		final User user = userService.findByUsername(username);
		return user == null ? Collections.emptySet() : permissionService.getUrlPermissions(user);
	}
}
