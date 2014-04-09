package com.siemens.ctbav.intership.shop.exception.superadmin.importer;

import java.util.ArrayList;
import java.util.List;

public class InvalidProductsException extends Exception {

	private static final long serialVersionUID = 250044732040646499L;

	List<InvalidProductException> exceptions;

	public InvalidProductsException() {
		exceptions = new ArrayList<InvalidProductException>();
	}

	public List<InvalidProductException> getExceptions() {
		return exceptions;
	}
	
	public void addException(InvalidProductException invalidProductException){
		exceptions.add(invalidProductException);
	}
	
	public void clearExceptions(){
		exceptions.clear();
	}
}
