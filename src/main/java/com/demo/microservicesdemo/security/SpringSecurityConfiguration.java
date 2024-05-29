package com.demo.microservicesdemo.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//		1. All requests should be authenticated
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		
//		2. If a request is not authenticated, a web page is shown
		http.httpBasic(withDefaults());

//		3. CSRF(Cross-Site Request Forgery ) -> for POST, PUT
		// In a CSRF attack, an innocent end user is tricked by an attacker into
		// submitting a web request that they did not intend. This may cause actions to
		// be performed on the website that can include inadvertent client or server
		// data leakage, change of session state, or manipulation of an end user's
		// account.the CSRF protection enabled. Now the POST request will simply fail if
		// the CSRF token isn't included, which of course means that the earlier attacks
		// are no longer an option.
		http.csrf().disable();

		return http.build();
	}

}
