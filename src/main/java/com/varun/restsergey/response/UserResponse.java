package com.varun.restsergey.response;

import java.util.List;

public class UserResponse {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<UserAddressResponse> address;
	
	public String getUserId() {
		return userId;
	}

	public List<UserAddressResponse> getAddress() {
		return address;
	}

	public void setAddress(List<UserAddressResponse> address) {
		this.address = address;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
