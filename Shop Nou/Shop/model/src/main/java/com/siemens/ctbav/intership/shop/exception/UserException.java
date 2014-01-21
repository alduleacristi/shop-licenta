package com.siemens.ctbav.intership.shop.exception;

public class UserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6251099772244721777L;
	
	public UserException(){
		super();
	}
	
	public UserException(String message){
		super(message);
	}
	
	public UserException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
