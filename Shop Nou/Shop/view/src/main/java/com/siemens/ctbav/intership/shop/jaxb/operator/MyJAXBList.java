package com.siemens.ctbav.intership.shop.jaxb.operator;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="list")
@XmlSeeAlso({ClientJAXB.class,OrderJAXB.class})
public class MyJAXBList {

	
	private List<? extends Object> list;

	public List<? extends Object> getList() {
		return list;
	}

	@XmlElementWrapper
	public void setList(List<? extends Object> list){
		this.list=list;
	}
	public MyJAXBList(List<? extends Object> list){
		this.list=list;
	}
	
	public MyJAXBList(){
		
	}
	
}
