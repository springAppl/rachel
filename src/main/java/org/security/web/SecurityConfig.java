package org.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService())
                .addFilterAfter(fingerPrintFilter(), SecurityContextPersistenceFilter.class)
                .authenticationProvider(fingerPrintProvider())
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/test").authenticated()
                //基于controller的登陆,登出配置
                .mvcMatchers(HttpMethod.POST, "/api/login").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/api/logout").authenticated()
                .and()
                .formLogin()
                .disable()
                .logout()
                .disable()
                .cors()
                .disable()
                .csrf()
                .disable();
    }
    @Bean
    public FingerPrintFilter fingerPrintFilter(){
        return new FingerPrintFilter();
    }

    @Bean
    public FingerPrintProvider fingerPrintProvider(){
        return new FingerPrintProvider();
    }

    @Bean
    public UserDetailsService userDetailsService(UserDao userDao){
        return new FingerPrintUserDetailsService(userDao);
    }
}

