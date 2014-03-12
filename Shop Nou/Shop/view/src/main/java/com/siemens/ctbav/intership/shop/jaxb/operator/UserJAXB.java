package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.enums.operator.PasswordStatus;

@XmlRootElement(name="user")

public class UserJAXB {

	private String username;
	
	private String email;
	
	private String password;
	
	private String rolename;
	
	private PasswordStatus passwordStatus;
	
	public UserJAXB(){
		
	}
	public UserJAXB(UserDTO user){
		this.email= user.getEmail();
		this.password=user.getPassword();
		this.passwordStatus=user.getPasswordStatus();
		this.rolename=user.getRolename();
		this.username=user.getUsername();
	}
	
	public String getUsername() {
		return username;
	}
	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRolename() {
		return rolename;
	}
	
	@XmlElement
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public PasswordStatus getPasswordStatus() {
		return passwordStatus;
	}
	
	@XmlElement
	public void setPasswordStatus(PasswordStatus passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	@Override
	public String toString() {
		return "UserJAXB [username=" + username + ", email=" + email
				+ ", password=" + password + ", rolename=" + rolename
				+ ", passwordStatus=" + passwordStatus + "]";
	}
	
	
}
