package com.siemens.ctbav.intership.shop.exception.client;

public class NullMessageException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6161986262125975841L;
	
	public NullMessageException(){
		
	}
	
	public NullMessageException(String msg){
		super(msg);
	}

}
