package com.siemens.ctbav.intership.shop.dto.operator;

import java.util.Date;
import java.util.List;

import com.siemens.ctbav.intership.shop.model.Command;

public class ReturnedOrdersDTO {

	private Long id;
	private CommandDTO command;
	private Date returnDate;
	private Date addDate;
	private boolean returned;
	private boolean retreived;
	
	public ReturnedOrdersDTO(){
		
	}
	public ReturnedOrdersDTO(Long id, CommandDTO command, Date returnDate, boolean retreived) {
		this.id=id;
		this.command = command;
		this.returnDate = returnDate;
		this.addDate=null;
		this.returned=false;
		this.retreived=retreived;
		
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
	public Long getId() {
		return id;
	}
	public boolean isRetreived() {
		return retreived;
	}
	public void setRetreived(boolean retreived) {
		this.retreived = retreived;
	}
	
	@Override
	public String toString() {
		return "ReturnedOrdersDTO [id=" + id + ", command=" + command
				+ ", returnDate=" + returnDate + ", addDate=" + addDate
				+ ", returned=" + returned + "]";
	}
	
}
