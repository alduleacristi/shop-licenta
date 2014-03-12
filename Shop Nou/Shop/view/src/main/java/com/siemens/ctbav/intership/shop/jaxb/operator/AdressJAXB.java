package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.AdressDTO;

@XmlRootElement(name="adress")
public class AdressJAXB {

	

	private String locality, staircase;
	private Long number, block, flat;
	
	
	public AdressJAXB(){
		
	}
	
	public AdressJAXB(AdressDTO adress){
		this.locality=adress.getLocality();
		this.staircase=adress.getStaircase();
		this.number=adress.getNumber();
		this.block=adress.getBlock();
		this.flat=adress.getFlat();
	}

	public String getLocality() {
		return locality;
	}

	@XmlElement
	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getStaircase() {
		return staircase;
	}

	@XmlElement
	public void setStaircase(String staircase) {
		this.staircase = staircase;
	}

	public Long getNumber() {
		return number;
	}

	@XmlElement
	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getBlock() {
		return block;
	}

	@XmlElement
	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getFlat() {
		return flat;
	}

	@XmlElement
	public void setFlat(Long flat) {
		this.flat = flat;
	}
	
	
}
