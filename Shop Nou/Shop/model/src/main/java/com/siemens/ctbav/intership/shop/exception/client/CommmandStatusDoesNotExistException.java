package com.siemens.ctbav.intership.shop.exception.client;

public class CommmandStatusDoesNotExistException extends Exception{

	private static final long serialVersionUID = -874066495793617209L;
	
	public CommmandStatusDoesNotExistException(){
		
	}
	
	public CommmandStatusDoesNotExistException(String msg){
		super(msg);
	}

}
