package com.siemens.ctbav.intership.shop.view.client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ViewScoped
@ManagedBean(name="updateClient")
public class ClientBean {
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9._-]{5,15}",message="The password must begin with a letter and it must have at least 6 characters.")
	private String password;
	
	private String retypedPassword;

	@Size(min=3 , message="First name must have at least 3 characters.")
	private String firstName;
	
	@Size(min=3 , message="Last name must have at least 3 characters.")
	private String lastName;
	
	@Pattern(regexp = "[0-9]{10,13}" , message="The phone number must have between 10-13 character and all must be digits.")
	private String phone;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}

