package com.varun.restsergey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.varun.restsergey.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
