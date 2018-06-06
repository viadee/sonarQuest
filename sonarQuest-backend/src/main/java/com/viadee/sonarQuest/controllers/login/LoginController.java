package com.viadee.sonarQuest.controllers.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.controllers.PathConstants;
import com.viadee.sonarQuest.security.JwtHelper;

@RestController
@RequestMapping(PathConstants.LOGIN_URL)
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @RequestMapping(method = RequestMethod.GET)
    public String info() {
        return "Dies ist eine Login Seite";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Token login(@Valid @RequestBody final UserCredentials credentials) {
        final User authenticatedUser = authentificateUser(credentials);
        return createTokenForUser(authenticatedUser);
    }

    private User authentificateUser(final UserCredentials credentials) {
        final Authentication authentication = authenticate(credentials);
        return (User) authentication.getPrincipal();
    }

    private Authentication authenticate(final UserCredentials credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
    }

    private Token createTokenForUser(final User user) {
        return jwtHelper.createTokenForUser(user.getUsername());
    }

}
