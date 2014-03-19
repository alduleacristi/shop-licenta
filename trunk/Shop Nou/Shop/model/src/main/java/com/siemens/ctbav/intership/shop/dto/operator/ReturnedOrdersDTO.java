package com.siemens.ctbav.intership.shop.dto.operator;

import java.util.Date;
import java.util.List;

import com.siemens.ctbav.intership.shop.model.Command;

public class ReturnedOrdersDTO {

	
	private CommandDTO command;
	private Date returnDate;
	private Date addDate;
	private boolean returned;
//	private List<ReturnedProductsDTO> productsList;
	
	public ReturnedOrdersDTO(){
		
	}
	public ReturnedOrdersDTO(CommandDTO command, Date returnDate) {
		this.command = command;
		this.returnDate = returnDate;
		//this.productsList=productsList;
		this.addDate=null;
		this.returned=false;
	}
	
	
	public CommandDTO getCommand() {
		return command;
	}
	public void setCommand(CommandDTO command) {
		this.command = command;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public boolean isReturned() {
		return returned;
	}
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
}
