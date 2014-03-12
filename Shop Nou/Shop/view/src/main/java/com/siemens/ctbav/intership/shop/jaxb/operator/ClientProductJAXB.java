package com.siemens.ctbav.intership.shop.jaxb.operator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;

@XmlRootElement(name="productsList")
public class ClientProductJAXB {

	private Long nrPieces;
	private Double percReduction;
	private Double price;
	private ProductColorSizeJAXB product;

	public ClientProductJAXB() {

	}

	public ClientProductJAXB(ClientProductDTO cp) {
		this.nrPieces = cp.getNrPieces();
		this.percReduction=cp.getPercReduction();
		this.price=cp.getPrice();
		this.product= new ProductColorSizeJAXB(cp.getProduct());
	}

	public Long getNrPieces() {
		return nrPieces;
	}

	@XmlElement
	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}

	public Double getPercReduction() {
		return percReduction;
	}

	@XmlElement
	public void setPercReduction(Double percReduction) {
		this.percReduction = percReduction;
	}

	public Double getPrice() {
		return price;
	}

	@XmlElement
	public void setPrice(Double price) {
		this.price = price;
	}

	public ProductColorSizeJAXB getProduct() {
		return product;
	}

	@XmlElement
	public void setProduct(ProductColorSizeJAXB product) {
		this.product = product;
	}

	
}
