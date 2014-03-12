package com.siemens.ctbav.intership.shop.dto.superadmin.exporter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.superadmin.exporter.ProductDto;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsDto {
	@XmlElement(name = "product")
	private ArrayList<ProductDto> products = null;

	public ProductsDto() {
	}

	public ProductsDto(List<ProductColorSize> pcss) {
		products = new ArrayList<ProductDto>();
		for (ProductColorSize pcs : pcss) {
			products.add(new ProductDto(pcs));
		}
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductDto> products) {
		this.products = products;
	}

}
