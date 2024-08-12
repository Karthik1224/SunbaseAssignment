package com.sunbase.assignment.modal;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer {

	

	@Id
	@Column(name = "Id",nullable=false)
	private String id;
	
	@Column(name = "FirstName",nullable=false)
	private String firstName;
	
	@Column(name = "LastName",nullable=false)
	private String lastName;
	
	@Column(name = "Street",nullable=true)
	private String street;
	
	@Column(name = "Address",nullable=false)
	private String address;
	
	@Column(name = "City",nullable=false)
	private String city;
	
	@Column(name = "State",nullable=false)
	private String state;
	
	@Column(name = "Email",nullable=false)
	private String email;
	
	@Column(name = "Phone",nullable=false)
	private String phone;
	
	

	public Customer() {
		super();
	}



	public Customer(String id,String firstName, String lastName, String street, String address, String city,
			String state, String email, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.phone = phone;
	}

	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
