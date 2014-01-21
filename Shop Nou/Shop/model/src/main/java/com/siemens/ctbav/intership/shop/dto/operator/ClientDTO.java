package com.siemens.ctbav.intership.shop.dto.operator;

import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;

//clsa dto corespunzatoare clasei Client din pachetul model
public class ClientDTO {

	private String firstname;
	private String lastname;
	private String phoneNumber;
	// private String email;
	private UserDTO user;

	public ClientDTO(String firstname, String lastname, String phoneNumber,
			UserDTO user) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		// this.email = email;
		this.user = user;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
		return "ClientDTO [firstname=" + firstname + ", lastname=" + lastname
				+ ", phoneNumber=" + phoneNumber + ", user=" + user + "]";
	}

}
