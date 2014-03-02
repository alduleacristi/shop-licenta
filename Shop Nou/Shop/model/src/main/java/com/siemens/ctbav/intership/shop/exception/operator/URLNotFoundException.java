package com.siemens.ctbav.intership.shop.exception.operator;

public class URLNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public URLNotFoundException() {
		super("Can't get the URL");
	}

	public URLNotFoundException(String arg0) {
		super(arg0);
	}

	public URLNotFoundException(Throwable arg0) {
		super(arg0);
	}

	
}
