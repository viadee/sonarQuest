package com.viadee.sonarquest.controllers.login;

import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.controllers.PathConstants;
import com.viadee.sonarquest.security.JwtHelper;

@RestController
@RequestMapping(PathConstants.LOGIN_URL)
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping
    public String info() {
        return "Dies ist eine Login Seite";
    }

    @PostMapping
    public Token login(@Valid @RequestBody final UserCredentials credentials) {

        String username = credentials.getUsername();
        LOGGER.info("Log-In request received from user {}", Objects.hashCode(username));
        final User authenticatedUser = authentificateUser(credentials);
        final Token token = createTokenForUser(authenticatedUser);
        LOGGER.info("Log-In request successful for user {}", Objects.hashCode(username));
        return token;
    }

    private User authentificateUser(final UserCredentials credentials) {
        final Authentication authentication = authenticate(credentials);
        return (User) authentication.getPrincipal();
    }

    private Authentication authenticate(final UserCredentials credentials) {
        String username = credentials.getUsername();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                credentials.getPassword());
        try {
            return authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            LOGGER.warn(String.format("Log-In request denied with bad credentials for user %s",
                    Objects.hashCode(username)));
            throw ex;
        }
    }

    private Token createTokenForUser(final User user) {
        return jwtHelper.createTokenForUser(user.getUsername(), user.getAuthorities());
    }

}
