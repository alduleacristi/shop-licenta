package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.ColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductDTO;

@XmlRootElement(name="productColor")

public class ProductColorJAXB {

	private ProductJAXB product;
	private String color;
	
	public ProductColorJAXB(){
		
	}
	
	public ProductColorJAXB(ProductColorDTO prod){
		ProductJAXB p= new ProductJAXB(prod.getProduct());
		product =p;
		color=prod.getColor().getName();
	}

	public ProductJAXB getProduct() {
		return product;
	}

	@XmlElement
	public void setProduct(ProductJAXB product) {
		this.product = product;
	}

	public String getColor() {
		return color;
	}

	@XmlElement
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
