package com.siemens.ctbav.intership.shop.exception.client;

import java.util.ArrayList;
import java.util.List;

public class NotEnoughProductsInStockException extends Exception{
	
	private static final long serialVersionUID = 7542609706004092313L;
	
	private List<String> messages;
	
	public NotEnoughProductsInStockException(){
		messages = new ArrayList<String>();
	}
	
	public NotEnoughProductsInStockException(String msg){
		super(msg);
		messages = new ArrayList<String>();
	}
	
	public void addMessage(String msg){
		messages.add(msg);
	}

	public List<String> getMessages() {
		return messages;
	}

}
