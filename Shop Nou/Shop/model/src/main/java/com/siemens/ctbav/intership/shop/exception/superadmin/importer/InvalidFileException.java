package com.siemens.ctbav.intership.shop.exception.superadmin.importer;

public class InvalidFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8191235685246203147L;

	public InvalidFileException() {

	}

	public InvalidFileException(String msg) {
		super(msg);
	}

	public InvalidFileException(StackTraceElement[] stackTrace) {
		super();
		setStackTrace(stackTrace);
	}

}
