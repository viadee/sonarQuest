package com.viadee.sonarQuest.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.viadee.sonarQuest.controllers.login.Token;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.algorithm}")
    private String algorithm;

    @Value("${security.jwt.validity}")
    private Long validity;

    public final Token createTokenForUser(final String username) {
        final long now = System.currentTimeMillis();
        final long expiresAt = now + validity;
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);

        final String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiresAt))
                .signWith(signatureAlgorithm, getSigningKey())
                .compact();

        return new Token(jwt, expiresAt);
    }

    public final String getUsernameFromJwt(final String jwt) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    private byte[] getSigningKey() {
        return Base64.getDecoder().decode(secret);
    }

}
