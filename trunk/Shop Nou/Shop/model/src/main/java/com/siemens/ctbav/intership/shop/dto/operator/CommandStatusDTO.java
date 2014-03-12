package com.siemens.ctbav.intership.shop.dto.operator;

public class CommandStatusDTO {

	private String status;

	public CommandStatusDTO() {
		this.status = "In progress";
	}

	public CommandStatusDTO(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "status=" + status;
	}

	
}
