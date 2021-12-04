package com.varun.restsergey.controller;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.varun.restsergey.dto.UserDto;
import com.varun.restsergey.exception.UserServiceException;
import com.varun.restsergey.request.UserRequest;
import com.varun.restsergey.response.OperationStatusModel;
import com.varun.restsergey.response.RequestOperationStatus;
import com.varun.restsergey.response.UserResponse;
import com.varun.restsergey.service.UserService;

@RestController
public class RestController1{

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<UserResponse> getUsers() {

		List<UserDto> dtoList = userService.getUser();

		List<UserResponse> respList = new ArrayList<UserResponse>();

		for (UserDto dto : dtoList) {
			UserResponse response = new UserResponse();
			BeanUtils.copyProperties(dto, response);
			respList.add(response);
		}

		return respList;
	}

	@GetMapping("/users/{userId}")
	public UserResponse getUsers(@PathVariable String userId) {

		UserDto user = userService.getUserByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		
		UserResponse res = new UserResponse();
		res = mapper.map(user,UserResponse.class);

		//BeanUtils.copyProperties(user, res);
		return res;

	}

	@PutMapping("/users/{userId}")
	public UserResponse updateUser(@PathVariable String userId, @RequestBody UserRequest req)
			throws UserServiceException {

		UserDto dto = new UserDto();

		BeanUtils.copyProperties(req, dto);

		dto = userService.updateUser(userId, dto);

		UserResponse res = new UserResponse();

		BeanUtils.copyProperties(dto, res);

		return res;
	}

	@PostMapping("/users")
	public UserResponse createUsers(@RequestBody UserRequest req) throws Exception {

		//UserDto userdto = new UserDto();
		
		ModelMapper mapper = new ModelMapper();
		UserDto userdto = mapper.map(req, UserDto.class);
		
		//BeanUtils.copyProperties(req, dto);
				
		UserDto createddto = userService.createUser(userdto);
		
		UserResponse res = mapper.map(createddto, UserResponse.class);
						
		return res;

	}

	@DeleteMapping("/users/{userId}")
	public OperationStatusModel deleteUser(@PathVariable String userId) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(userId);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

}
