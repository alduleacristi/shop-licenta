package com.siemens.ctbav.intership.shop.exception;

public class ClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6251099772244721777L;
	
	public ClientException(){
		super();
	}
	
	public ClientException(String message){
		super(message);
	}
	
	public ClientException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
