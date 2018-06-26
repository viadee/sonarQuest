package com.viadee.sonarQuest.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Permission;
import com.viadee.sonarQuest.entities.PermissionType;
import com.viadee.sonarQuest.entities.User;

@Service
public class PermissionService {

    public Set<Permission> getUrlPermissions(final User user) {
        return user.getRole().getPermissions().stream()
                .filter(permission -> permission.getType() == PermissionType.URL)
                .collect(Collectors.toSet());
    }

}
