package com.varun.restsergey.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.varun.restsergey.entity.ExceptionEntity;

@ControllerAdvice
public class UserExceptionHandler {
	


	@ExceptionHandler
	public ResponseEntity<Object> invalidNameException(UserServiceException ex) {
		
		ExceptionEntity exen = new ExceptionEntity(new Date(System.currentTimeMillis()), ex.getMessage());
		
		return new ResponseEntity<>(exen, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}


	@ExceptionHandler
	public ResponseEntity<Object> generalException(Exception ex) {
		
		ExceptionEntity exen = new ExceptionEntity(new Date(System.currentTimeMillis()), ex.getMessage());
		
		return new ResponseEntity<>(exen, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	
	
	

}
