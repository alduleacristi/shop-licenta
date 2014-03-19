package com.siemens.ctbav.intership.shop.exception.client;

public class AddToCartFailedException extends Exception{

	private static final long serialVersionUID = 1791073289666375406L;
	
	public AddToCartFailedException(){
		
	}
	
	public AddToCartFailedException(String msg){
		super(msg);
	}

}
