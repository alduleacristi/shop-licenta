package com.siemens.ctbav.intership.shop.exception.client;

public class ClientDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2775743841452257257L;
	
	public ClientDoesNotExistException(){
		
	}
	
	public ClientDoesNotExistException(String msg){
		super(msg);
	}
	
}
