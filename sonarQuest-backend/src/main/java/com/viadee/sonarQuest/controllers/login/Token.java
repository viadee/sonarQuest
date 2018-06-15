package com.viadee.sonarQuest.controllers.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    private final String jwt;

    @JsonProperty("expires_at")
    private final long expiresAt;

    public Token(final String jwt, final long expiresAt) {
        super();
        this.jwt = jwt;
        this.expiresAt = expiresAt;
    }

    public String getJwt() {
        return jwt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

}
