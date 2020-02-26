package com.viadee.sonarquest.security;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public class UsernameAndAuthorities {
    private final String username;
    private final Collection<GrantedAuthority> authorities;
}
