package com.varun.restsergey.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.varun.restsergey.dto.UserDto;
@Entity(name = "addresses")
public class AddressEntity implements Serializable{

	private static final long serialVersionUID = 542911001113493264L;

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 100, nullable = false)
	private String street;
	
	@Column(length = 100, nullable = false)
	private String city;

	@Column(length = 100, nullable = false)
	private String country;
	
	@Column(length = 100, nullable = false)
	private String postalcode;

	@Column(length = 100, nullable = false)
	private String type;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}

	
}
