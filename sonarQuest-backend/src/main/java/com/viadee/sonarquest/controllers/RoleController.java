package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.services.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAllAvatarRaces() {
        return roleService.findAll();
    }
}
