package com.siemens.ctbav.intership.shop.dto.operator;

import com.siemens.ctbav.intership.shop.model.Locality;

public class AdressDTO {

	private String locality, staircase;
	private Long number, block, flat;
	public AdressDTO(String locality, String staircase, Long long1, Long long2, Long flat) {
		this.locality = locality;
		this.staircase = staircase;
		this.number = long1;
		this.block = long2;
		this.flat=flat;
	}
	public String getLocality() {
		return locality;
	}
	public String getStaircase() {
		return staircase;
	}
	public Long getNumber() {
		return number;
	}
	public Long getBlock() {
		return block;
	}
	public Long getFlat() {
		return flat;
	} 	
	
}
