package com.viadee.sonarQuest.security;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.viadee.sonarQuest.controllers.login.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

    private static final String AUTHORITIES = "authorities";

    private String secret = RandomStringUtils.randomAlphanumeric(60);

    @Value("${security.jwt.algorithm}")
    private String algorithm;

    @Value("${security.jwt.validity}")
    private Long validity;

    public final Token createTokenForUser(final String username, final Collection<GrantedAuthority> authorities) {
        final long now = System.currentTimeMillis();
        final long expiresAt = now + validity;
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);

        final Map<String, Object> authoriesClaim = new HashMap<>();
        authoriesClaim.put(AUTHORITIES, authorities);

        final String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiresAt))
                .addClaims(authoriesClaim)
                .signWith(signatureAlgorithm, getSigningKey())
                .compact();

        return new Token(jwt, expiresAt);
    }

    @SuppressWarnings("unchecked")
    public final UsernameAndAuthorities getUsernameWithAuthorities(final String jwt) {
        final Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(jwt)
                .getBody();
        final String username = claims.getSubject();
        final List<HashMap<String, String>> authoritiesMap = (List<HashMap<String, String>>) claims.get(AUTHORITIES);
        final Collection<GrantedAuthority> authorities = authoritiesMap.stream().flatMap(map -> map.values().stream())
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toSet());
        return new UsernameAndAuthorities(username, authorities);
    }

    private byte[] getSigningKey() {
        return Base64.getDecoder().decode(secret);
    }

}
