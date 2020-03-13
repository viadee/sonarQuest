package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.repositories.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(final RoleName name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
