package com.siemens.ctbav.intership.shop.exception.client;

public class NullUserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7722438601328171629L;
	
	public NullUserException(){
		
	}
	
	public NullUserException(String msg){
		super(msg);
	}

}
