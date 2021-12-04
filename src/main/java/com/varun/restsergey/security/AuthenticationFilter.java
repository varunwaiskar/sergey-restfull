package com.varun.restsergey.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.restsergey.SpringApplicationContext;
import com.varun.restsergey.dto.UserDto;
import com.varun.restsergey.request.UserLoginRequestModel;
import com.varun.restsergey.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	private AuthenticationManager authManager;
	
	public AuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			UserLoginRequestModel user = new ObjectMapper()
					.readValue(request.getInputStream(), UserLoginRequestModel.class);
			return authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String userName = ((User) authResult.getPrincipal()).getUsername(); 
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
				.compact();
		
		UserService service = (UserService)SpringApplicationContext.getBean("userServiceImpl");
		UserDto dto = service.getUser(userName);
		
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.addHeader("UserId", dto.getUserId());
	}
	
	
}
