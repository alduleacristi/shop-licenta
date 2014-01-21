package com.siemens.ctbav.intership.shop.exception.client;

public class NullClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182782810543648922L;
	
	public NullClientException(){
		
	}
	
	public NullClientException(String msg){
		super(msg);
	}

}
