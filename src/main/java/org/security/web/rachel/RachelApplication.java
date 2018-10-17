package org.security.web.rachel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableWebSecurity
@RestController
public class RachelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RachelApplication.class, args);
	}
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@PostMapping("/api/login")
	public void login(String name, String password){
		UserDetails userDetails = new CustomUserDetails(name, password);
		Authentication result = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getUsername(),
				authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
		SecurityContextHolder.getContext().setAuthentication(result);
	}

	@GetMapping("/api/test")
	public String test(){
		return "hello the world";
	}

	@Configuration
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.userDetailsService(userDetailsService())
					.antMatcher("/api/**")
					.authorizeRequests()
					.mvcMatchers(HttpMethod.GET, "/api/test").authenticated()
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
	}
}
