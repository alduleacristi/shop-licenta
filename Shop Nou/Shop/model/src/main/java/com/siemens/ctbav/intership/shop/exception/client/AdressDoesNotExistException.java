package com.siemens.ctbav.intership.shop.exception.client;

public class AdressDoesNotExistException extends Exception{

	private static final long serialVersionUID = -5010838852179707976L;
	
	public AdressDoesNotExistException(){
		
	}
	
	public AdressDoesNotExistException(String msg){
		super(msg);
	}

}
