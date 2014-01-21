package com.siemens.ctbav.intership.shop.exception;

public class CategoryException extends Exception{

	private static final long serialVersionUID = -1878071495267055811L;
	
	public CategoryException(){
		super();
	}
	
	public CategoryException (String message){
		super(message);
	}
	
	public CategoryException(String message, Throwable cause){
		super(message,cause);
	}
}
