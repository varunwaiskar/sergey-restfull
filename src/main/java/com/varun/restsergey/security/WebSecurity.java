package com.varun.restsergey.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.varun.restsergey.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.userService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;	
        
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL,"/")
				.permitAll()
			.anyRequest()
				.authenticated()
			.and() 
			.addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		
		AuthenticationFilter authFil = new 
								AuthenticationFilter(authenticationManager());
		authFil.setFilterProcessesUrl("/users/login");
		
		return authFil;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth
			.userDetailsService(userService)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	
}
