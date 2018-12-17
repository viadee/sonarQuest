package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.entities.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName name);
}
