package com.siemens.ctbav.intership.shop.dto.operator;

import java.util.Date;

import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;

//clsa dto corespunzatoare clasei Client din pachetul model
public class ClientDTO {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	// private String email;
	private UserDTO user;
	private Date lastOrderDate;
	
	
	public ClientDTO(String firstname, String lastname, String phoneNumber,
			UserDTO user) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.phoneNumber = phoneNumber;
		this.user = user;
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


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ClientDTO [firstname=" + firstName + ", lastname=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", user=" + user + "]";
	}

	public Date getLastOrderDate() {
		return lastOrderDate;
	}

	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

}
