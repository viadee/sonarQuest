package com.viadee.sonarquest.controllers.login;

import org.hibernate.validator.constraints.NotBlank;

public class UserCredentials {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public UserCredentials() {
        super();
    }

    public UserCredentials(final String username, final String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

}
