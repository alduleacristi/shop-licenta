package com.siemens.ctbav.intership.shop.exception.superadmin;

public class UserException extends Exception {

	private static final long serialVersionUID = 8334381645928984018L;

	public UserException() {
		super();
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

}
