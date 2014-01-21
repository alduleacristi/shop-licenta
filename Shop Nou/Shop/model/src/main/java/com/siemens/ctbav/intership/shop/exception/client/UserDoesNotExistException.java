package com.siemens.ctbav.intership.shop.exception.client;

public class UserDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6539312093993893906L;
	
	public UserDoesNotExistException(){
		
	}
	
	public UserDoesNotExistException(String msg){
		super(msg);
	}

}
