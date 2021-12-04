package com.varun.restsergey.service.implementation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.varun.restsergey.dto.AddressDto;
import com.varun.restsergey.dto.UserDto;
import com.varun.restsergey.entity.UserEntity;
import com.varun.restsergey.exception.ErrorMessages;
import com.varun.restsergey.exception.UserServiceException;
import com.varun.restsergey.repositories.UserRepository;
import com.varun.restsergey.request.UserAddress;
import com.varun.restsergey.response.UserAddressResponse;
import com.varun.restsergey.response.UserResponse;
import com.varun.restsergey.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userdto) {
		
		
		List<AddressDto> addresslist = userdto.getAddress();
		for(int i = 0; i <addresslist.size();i++) {
			AddressDto address = addresslist.get(i);
			address.setUserDetails(userdto);
		}
		
		userdto.setAddress(addresslist);
		
		ModelMapper mapper = new ModelMapper();
		
		UserEntity entity = mapper.map(userdto, UserEntity.class);
		
		entity.setEncryptedPassword(
				bCryptPasswordEncoder
				.encode(userdto.getPassword())
				);		
		entity.setUserId(
				entity.getFirstName().substring(0, 3)
				+ entity.getLastName().substring(0, 3)
				+ userdto.getPassword().substring(userdto.getPassword().length()-3 ,userdto.getPassword().length() )
				);		
		entity.setEmailVerificationStatus(false);
		
		UserEntity newentity = repo.save(entity);

		UserDto newdto = mapper.map(newentity, UserDto.class);
	
		return newdto;
		
	}
	
	@Override
	public UserDto getUser(String email) {
		
		
		
		UserEntity entity = repo.findByEmail(email);
		
		if(entity == null)
			throw new UsernameNotFoundException("no user with this id exists");
		
		//BeanUtils.copyProperties(entity, dto);
		ModelMapper mapper = new ModelMapper();
		UserDto dto = mapper.map(entity, UserDto.class);
		
		return dto;
	}
	
	@Override
	public List<UserDto> getUser() {

		List<UserEntity> userList = repo.findAll();
		
		List<UserDto> dtoList = new ArrayList<UserDto>();
		
		for(UserEntity en : userList) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(en, dto);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity user = repo.findByEmail(email);
		
		if(user.equals(null) || user == null)
			throw new UsernameNotFoundException("no user with this id exists");
		
		else
			return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
		
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		
		UserEntity user = repo.findByUserId(userId);
		
		if (user == null)
			throw new UsernameNotFoundException("User with ID: " + userId + " not found");

		
		ModelMapper mapper = new ModelMapper();

		UserDto dto =new UserDto();
		dto = mapper.map(user,UserDto.class);
		
		return dto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto dto) {

		UserEntity user = repo.findByUserId(userId);
		

		if (user == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		

		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());

		user = repo.save(user);

		BeanUtils.copyProperties(user, dto);

		return dto;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = repo.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		repo.delete(userEntity);

	}
}
