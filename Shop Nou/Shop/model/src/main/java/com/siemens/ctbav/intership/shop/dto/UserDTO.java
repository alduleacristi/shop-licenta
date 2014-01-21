package com.siemens.ctbav.intership.shop.dto;

public class UserDTO {

	private String username, email;

	
	public UserDTO(String username, String email) {
		this.username = username;
		this.email=email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", email=" + email + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
