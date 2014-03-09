package com.siemens.ctbav.intership.shop.exception.client;

public class CommandDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4238025284649709724L;
	
	public CommandDoesNotExistException(){
		
	}
	
	public CommandDoesNotExistException(String msg){
		super(msg);
	}

}
