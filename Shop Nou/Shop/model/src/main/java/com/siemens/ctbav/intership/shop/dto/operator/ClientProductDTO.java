package com.siemens.ctbav.intership.shop.dto.operator;

public class ClientProductDTO {

	private Long nrPieces;
	private Double percReduction;
	private Double price;
	private ProductColorSizeDTO product;
	
	public ClientProductDTO(Long nrPieces, Double percRedution, Double price,
			ProductColorSizeDTO product) {
		this.setNrPieces(nrPieces);
		this.percReduction = percRedution;
		this.price = price;
		this.product = product;
	}

	
	public Double getPercReduction() {
		return percReduction;
	}

	public void setPercReduction(Double percReduction) {
		this.percReduction = percReduction;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ProductColorSizeDTO getProduct() {
		return product;
	}

	public void setProduct(ProductColorSizeDTO product) {
		this.product = product;
	}


	public Long getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}
	
	public Double totalPrice(){
		return price * nrPieces;
	}
}
