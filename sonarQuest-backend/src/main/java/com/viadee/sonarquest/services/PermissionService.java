package com.viadee.sonarquest.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viadee.sonarquest.constants.PermissionType;
import com.viadee.sonarquest.entities.Permission;
import com.viadee.sonarquest.entities.User;

@Service
public class PermissionService {

    public Set<Permission> getUrlPermissions(final User user) {
        return user.getRole().getPermissions().stream()
                .filter(permission -> permission.getType() == PermissionType.URL)
                .collect(Collectors.toSet());
    }

    public Set<Permission> getAccessPermissions(final User user) {
        return user.getRole().getPermissions().stream()
                .filter(permission -> permission.getType() == PermissionType.ACCESS)
                .collect(Collectors.toSet());
    }

}
