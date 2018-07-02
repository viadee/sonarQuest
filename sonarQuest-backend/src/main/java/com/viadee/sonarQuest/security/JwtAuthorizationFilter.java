package com.viadee.sonarQuest.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    private final JwtHelper jwtHelper;

    public JwtAuthorizationFilter(final JwtHelper jwtHelper) {
        super();
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;

        final String authHeader = getAuthorizationHeader(request);
        if (isBearerAuthentication(authHeader)) {
            authentication = createAuthentication(authHeader);
        }

        if (authentication == null) {
            SecurityContextHolder.clearContext();
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private String getAuthorizationHeader(final HttpServletRequest request) {
        return request.getHeader(HEADER_STRING);
    }

    private boolean isBearerAuthentication(final String authHeader) {
        return authHeader != null && authHeader.startsWith(TOKEN_PREFIX);
    }

    private Authentication createAuthentication(final String authHeader) {
        UsernamePasswordAuthenticationToken token = null;
        final UsernameAndAuthorities usernameAndAuthorities = getUsernameAndAuthorities(authHeader);
        final String username = usernameAndAuthorities.getUsername();
        final Collection<GrantedAuthority> authorities = usernameAndAuthorities.getAuthorities();
        if (username != null) {
            token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return token;
    }

    private UsernameAndAuthorities getUsernameAndAuthorities(final String authHeader) {
        UsernameAndAuthorities username = null;
        final String jwt = authHeader.replace(TOKEN_PREFIX, "");
        try {
            username = jwtHelper.getUsernameWithAuthorities(jwt);
        } catch (final JwtException e) {
            LOGGER.error("Fehler bei der Validierung des JWT: " + e.getMessage());
        }
        return username;
    }

}
