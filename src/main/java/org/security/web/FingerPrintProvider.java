package org.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

public class FingerPrintProvider implements AuthenticationProvider {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FingerPrintToken fingerPrintToken = (FingerPrintToken) authentication;
        // TODO 获取UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        FingerPrintUserDetails fingerPrintUserDetails = (FingerPrintUserDetails) userDetails;
        // TODO 校验指纹
        if (!Objects.equals(fingerPrintToken.getFingerPrint(), fingerPrintUserDetails.getFingerPrint())){
            throw new BadCredentialsException("指纹不匹配");
        }
        // 重新注入Authentication
        fingerPrintToken.setPrincipal(userDetails);
        fingerPrintToken.setAuthenticated(true);
        return fingerPrintToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FingerPrintToken.class.isAssignableFrom(aClass);
    }
}
