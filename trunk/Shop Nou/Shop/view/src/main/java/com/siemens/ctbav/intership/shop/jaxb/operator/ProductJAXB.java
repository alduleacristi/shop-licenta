package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.ProductDTO;

@XmlRootElement(name="product")
public class ProductJAXB {

	private String name, description;
	private Double price;
	private String category;
	
	public ProductJAXB(){
		
	}
	
	public ProductJAXB(ProductDTO product){
		this.name=product.getName();
		this.description=product.getDescription();
		this.price=product.getPrice();
		this.category=product.getCategory().getName();
	}

	

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	@XmlElement
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	@XmlElement
	public void setCategory(String category) {
		this.category = category;
	}
}
