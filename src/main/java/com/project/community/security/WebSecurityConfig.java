package com.project.community.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final AuthenticationFailureHandler customFailureHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(13);
	}
	
	@Bean
	UserDetailsService empDetailsService() {
		return new EmployeeDetailsService();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable())

		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/img/**", "/js/**","/sign/**","/favicon.ico","/error").permitAll()
				//.requestMatchers("/employee/**").hasRole("SUPERVISOR")
				.requestMatchers("/**").permitAll()
				.anyRequest().permitAll())
		
		.formLogin(formLogin -> formLogin
				.loginPage("/sign")
				.loginProcessingUrl("/sign/signin")
				.usernameParameter("empUsername")
				.passwordParameter("empPassword")
				.defaultSuccessUrl("/")
				.failureHandler(customFailureHandler)
				.permitAll())
		
		.logout(logout -> logout
				.logoutSuccessUrl("/sign")
				.invalidateHttpSession(true))
		
		.exceptionHandling(accessDenied -> accessDenied
				.accessDeniedPage("/accessDenied"))
		;
		
		return http.build();
	}
}