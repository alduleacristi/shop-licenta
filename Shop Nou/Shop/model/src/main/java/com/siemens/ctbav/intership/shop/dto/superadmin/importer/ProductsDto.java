package com.siemens.ctbav.intership.shop.dto.superadmin.importer;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType (XmlAccessType.FIELD)
public class ProductsDto 
{
	@XmlElement(name = "product")
	private List<ProductDto> products = null;


	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

}