package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.ProductColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.dto.operator.SizeDTO;

@XmlRootElement(name="producColorSize")
public class ProductColorSizeJAXB {

	private ProductColorJAXB prodColor;
	private String size;
	private Long nrPieces;
	
	public ProductColorSizeJAXB(){
		
	}
	
	public ProductColorSizeJAXB(ProductColorSizeDTO prod){
		this.size=prod.getSize().getName();
		this.nrPieces=prod.getNrPieces();
		this.prodColor=new ProductColorJAXB(prod.getProdColor());
	}

	public ProductColorJAXB getProdColor() {
		return prodColor;
	}

	@XmlElement
	public void setProdColor(ProductColorJAXB prodColor) {
		this.prodColor = prodColor;
	}

	public String getSize() {
		return size;
	}

	@XmlElement
	public void setSize(String size) {
		this.size = size;
	}

	public Long getNrPieces() {
		return nrPieces;
	}

	@XmlElement
	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}
	
	
	
}
