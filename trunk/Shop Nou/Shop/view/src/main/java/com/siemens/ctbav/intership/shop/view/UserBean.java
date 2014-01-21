package com.siemens.ctbav.intership.shop.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "User")
@SessionScoped
public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8004807451931699460L;

	@Override
	public String toString() {
		return "UserBean [userName=" + userName + ", password=" + password
				+ "]";
	}

	private String userName,password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
