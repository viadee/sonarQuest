package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.Role;
import com.viadee.sonarQuest.entities.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName name);
}
