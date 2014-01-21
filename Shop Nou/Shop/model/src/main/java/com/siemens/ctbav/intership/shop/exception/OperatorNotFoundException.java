package com.siemens.ctbav.intership.shop.exception;

public class OperatorNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2940104761463670819L;
	
	public OperatorNotFoundException()
	{
		super("The operator cnnot be found in the databse!!!");
	}
	
	public OperatorNotFoundException(Throwable cause)
	{
		super("The operator cnnot be found in the databse!!!", cause);
	}
	
	

}
