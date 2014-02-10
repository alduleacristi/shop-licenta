package com.siemens.ctbav.intership.shop.exception.client;

public class ProductColorSizeDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4448622259847182777L;
	
	public ProductColorSizeDoesNotExistException(){
		
	}

	public ProductColorSizeDoesNotExistException(String msg){
		super(msg);
	}
}
