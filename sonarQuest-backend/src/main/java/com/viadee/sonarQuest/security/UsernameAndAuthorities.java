package com.viadee.sonarQuest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UsernameAndAuthorities {

    private final String username;

    private final Collection<GrantedAuthority> authorities;

    public UsernameAndAuthorities(final String username, final Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
