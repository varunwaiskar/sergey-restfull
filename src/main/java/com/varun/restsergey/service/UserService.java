package com.varun.restsergey.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.varun.restsergey.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto dto);
	UserDto getUser(String email);
	List<UserDto> getUser();
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto dto);
	void deleteUser(String userId);

}
