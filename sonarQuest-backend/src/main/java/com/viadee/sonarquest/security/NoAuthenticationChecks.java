package com.viadee.sonarquest.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class NoAuthenticationChecks implements UserDetailsChecker {

    @Override
    public void check(final UserDetails toCheck) {
        // No checks here...
    }

}
