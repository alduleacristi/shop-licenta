package com.siemens.ctbav.intership.shop.dto.operator;

public class UserDTO {

	private String username, email, password, rolename;
	private int passwordStatus;

	
	

	public UserDTO(String username, String email, String password,
			String rolename, int passwordStatus) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.rolename = rolename;
		this.passwordStatus = passwordStatus;
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
		System.out.println("get email " + email);
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(int passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	
	
}
