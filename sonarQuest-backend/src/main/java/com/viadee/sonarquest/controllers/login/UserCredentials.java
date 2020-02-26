package com.viadee.sonarquest.controllers.login;

import lombok.*;

@Data
@AllArgsConstructor
public class UserCredentials {
    private String username;
    private String password;
}
