package com.siemens.ctbav.intership.shop.dto.operator;

public class OperatorDTO {

	
	private String username , password, retypePassword, email;

	public OperatorDTO() {
	}

	
	public OperatorDTO(String username, String password, String retypePassword,
			String email) {
		this.username = username;
		this.password = password;
		this.retypePassword = retypePassword;
		this.email = email;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		System.out.println("set userneme " + username);
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		System.out.println("set password " + password);
		this.password = password;
	}

	

	@Override
	public String toString() {
		return "OperatorDTO [username=" + username + ", password=" + password
				+ ", email=" + email + "]";
	}


	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((retypePassword == null) ? 0 : retypePassword.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperatorDTO other = (OperatorDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (retypePassword == null) {
			if (other.retypePassword != null)
				return false;
		} else if (!retypePassword.equals(other.retypePassword))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
