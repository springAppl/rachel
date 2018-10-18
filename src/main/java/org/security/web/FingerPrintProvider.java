package org.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class FingerPrintProvider implements AuthenticationProvider {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FingerPrintToken fingerPrintToken = (FingerPrintToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        fingerPrintToken.setPrincipal(userDetails);
        fingerPrintToken.setAuthenticated(true);
        return fingerPrintToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        Boolean bo = FingerPrintToken.class.isAssignableFrom(aClass);
        return bo;
    }
}
