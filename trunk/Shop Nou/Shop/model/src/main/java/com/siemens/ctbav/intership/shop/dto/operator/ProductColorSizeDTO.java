package com.siemens.ctbav.intership.shop.dto.operator;

public class ProductColorSizeDTO {

	private ProductColorDTO prodColor;
	private SizeDTO size;
	private Long nrPieces;
	public ProductColorSizeDTO(ProductColorDTO prodColor, SizeDTO size, Long nrPieces) {
		this.prodColor = prodColor;
		this.size = size;
		this.nrPieces=nrPieces;
	}

	public ProductColorDTO getProdColor() {
		return prodColor;
	}

	public void setProdColor(ProductColorDTO prodColor) {
		this.prodColor = prodColor;
	}

	public SizeDTO getSize() {
		return size;
	}

	public void setSize(SizeDTO size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((prodColor == null) ? 0 : prodColor.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("in equals");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductColorSizeDTO other = (ProductColorSizeDTO) obj;
		if (prodColor == null) {
			if (other.prodColor != null)
				return false;
		} else if (!prodColor.equals(other.prodColor))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}

	public Long getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}

	@Override
	public String toString() {
		return "prodColor=" + prodColor + ", size=" + size
				+ ", nrPieces=" + nrPieces;
	}

	
}
