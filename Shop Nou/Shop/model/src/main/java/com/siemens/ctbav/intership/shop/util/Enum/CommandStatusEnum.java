package com.siemens.ctbav.intership.shop.util.Enum;

public enum CommandStatusEnum {
	IN_PROGRESS("In progress"),DELIVERED("delivered");
	
	private final String status;
	
	private CommandStatusEnum(final String status){
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.status;
	}
}
