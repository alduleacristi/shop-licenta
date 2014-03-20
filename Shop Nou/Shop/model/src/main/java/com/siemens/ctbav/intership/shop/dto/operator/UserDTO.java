package com.siemens.ctbav.intership.shop.dto.operator;

import com.siemens.ctbav.intership.shop.enums.operator.PasswordStatus;



public class UserDTO {

	private String username, email, password,retypePassword, rolename;
	private PasswordStatus passwordStatus;

	public UserDTO(){
		
	}
	public UserDTO(String username, String email, String password,
			String rolename, PasswordStatus passwordStatus) {
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

	public String getEmail() {
		return email;
	}

	public PasswordStatus getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(PasswordStatus i) {
		this.passwordStatus = i;
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

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Override
	public String toString() {
		return username + ";" + email
				+ ";" + password + ";" + rolename
				+ ";" + passwordStatus;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

}
