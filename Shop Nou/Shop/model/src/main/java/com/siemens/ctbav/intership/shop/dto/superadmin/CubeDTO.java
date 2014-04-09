package com.siemens.ctbav.intership.shop.dto.superadmin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.FIELD)
public class CubeDTO {
	@XmlAttribute
	private String currency;
	
	@XmlAttribute
	private String rate;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
}
