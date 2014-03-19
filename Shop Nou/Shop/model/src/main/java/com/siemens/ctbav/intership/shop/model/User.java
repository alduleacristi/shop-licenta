package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({
		@NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.GET_ALL_ADMINS, query = "SELECT u FROM User u where u.rolename = 'admin'"),
		@NamedQuery(name = User.GET_ALL_OPERATORS, query = "SELECT u FROM User u where u.rolename = 'operator'"),
		@NamedQuery(name = User.GET_CLIENT_USERS, query = "select u from User u where u.rolename='client'"),
		@NamedQuery(name = User.GET_USER_ID, query = "select u.id from User u where u.username=:user"),
		@NamedQuery(name = User.GET_CLIENT_BY_USERNAME, query = "select u from User u where u.username = :username"),
		@NamedQuery(name = User.GET_USER_BY_EMAIL, query = "select u from User u where u.email = :email and u.passwordStatus=1"),
		@NamedQuery(name = User.GET_USER_BY_PASSWORD, query = "select u from User u where u.userPassword = :password and u.passwordStatus =0") })
public class User implements Serializable {

	private static final long serialVersionUID = -1412776763797658827L;

	public static final String GET_ALL_USERS = "getAllUsers";
	public static final String GET_CLIENT_USERS = "get client users";
	public static final String GET_USER_ID = "get user id";
	public static final String GET_CLIENT_BY_USERNAME = "get user by username";
	public static final String GET_USER_BY_EMAIL = "get user by email";
	public static final String GET_USER_BY_PASSWORD = "get user by pssword";
	public static final String GET_ALL_ADMINS = "getAllAdmins";
	public static final String GET_ALL_OPERATORS = "getAllOperators";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column
	private String username;

	@Column(name = "user_password")
	private String userPassword;

	@Column
	private String rolename;

	@Column
	private String email;

	@Column
	private int passwordStatus;

	@Column
	private int ban;

	public User() {
	}

	public User(long id, String username, String userPassword, String rolename,
			String email) {
		this.id = id;
		this.username = username;
		this.userPassword = userPassword;
		this.rolename = rolename;
		this.email = email;
	}

	public User(String username, String userPassword, String rolename,
			String email) {
		this.username = username;
		this.userPassword = userPassword;
		this.rolename = rolename;
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(int passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	public int getBan() {
		return ban;
	}

	public void setBan(int ban) {
		this.ban = ban;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userPassword="
				+ userPassword + ", rolename=" + rolename + ", email=" + email
				+ ", passwordStatus=" + passwordStatus + "]";
	}

}
