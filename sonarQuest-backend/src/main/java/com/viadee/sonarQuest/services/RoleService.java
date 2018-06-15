package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Role;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(final RoleName name) {
        return roleRepository.findByName(name);
    }
}
