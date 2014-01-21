package com.siemens.ctbav.intership.shop.exception.client;

public class ProductDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -54807555066859032L;
	
	public ProductDoesNotExistException(){	
	}
	
	public ProductDoesNotExistException(String msg){
		super(msg);
	}

}
