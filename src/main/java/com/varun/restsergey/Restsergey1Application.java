package com.varun.restsergey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.varun.restsergey.security.ValueFromPropertiesFile;

@SpringBootApplication
public class Restsergey1Application {

	public static void main(String[] args) {
		SpringApplication.run(Restsergey1Application.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
	}
	
	
	//instead of creating spring application context bean 
	//we used @controller tag above SpringApplicationContext class
	
	/*
	 * @Bean public SpringApplicationContext springApplicationContext() { return new
	 * SpringApplicationContext(); }
	 */
	
	@Bean
	public ValueFromPropertiesFile getAppProperties()
	{
		return new ValueFromPropertiesFile();
	}
	

}
