package com.siemens.ctbav.intership.shop.jaxb.operator;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="list")
public class MyJAXBList {

	
	private List<? extends Object> list;

	public List<? extends Object> getList() {
		return list;
	}

	@XmlElement
	public void setList(List<? extends Object> list) {
		this.list = list;
	}
	
	
}
