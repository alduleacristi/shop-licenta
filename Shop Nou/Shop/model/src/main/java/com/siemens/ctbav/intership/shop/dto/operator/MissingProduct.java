package com.siemens.ctbav.intership.shop.dto.operator;


import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;

public class MissingProduct implements Comparable<MissingProduct> {

	private ProductColorSizeDTO product;
	private Long nrPieces;
	private Long piecesAdded;

	public MissingProduct(ProductColorSizeDTO product, Long nrPieces) {
		this.product = product;
		this.nrPieces = nrPieces;
	}

	public ProductColorSizeDTO getProduct() {
		return product;
	}

	public Long getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissingProduct other = (MissingProduct) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	public Long getPiecesAdded() {
		return piecesAdded;
	}

	public void setPiecesAdded(Long piecesAdded) {
		this.piecesAdded = piecesAdded;
	}

	@Override
	public int compareTo(MissingProduct other) {
		if(nrPieces == other.nrPieces) return 0;
		if(nrPieces < other.nrPieces) return -1;
		return 1;
	}

	
}
