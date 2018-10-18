package org.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class FingerPrintUserDetailsService implements UserDetailsService{

    private final UserDao userDao;

    @Autowired
    public FingerPrintUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optional = userDao.queryByName(s);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException(String.format("%s not find", s));
        }
        return new FingerPrintUserDetails(optional.get());
    }
}
