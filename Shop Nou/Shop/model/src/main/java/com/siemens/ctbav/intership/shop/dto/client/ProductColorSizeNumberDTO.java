package com.siemens.ctbav.intership.shop.dto.client;

import com.siemens.ctbav.intership.shop.model.ProductColor;

/**
 * This class is used in cart view to map ProductColorSize with number of
 * products which are stored in cart.
 * 
 * @author Cristi
 * 
 */
public class ProductColorSizeNumberDTO {
	private String name,color,size;
	private Double price,reduction;
	private Long nrOfPiecesInStock,id;
	private Integer nrOfPieces;
	private ProductColor productColor;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getNrOfPieces() {
		return nrOfPieces;
	}
	public void setNrOfPieces(Integer nrOfPieces) {
		this.nrOfPieces = nrOfPieces;
	}
	public Double getReduction() {
		return reduction;
	}
	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductColor getProductColor() {
		return productColor;
	}
	public void setProductColor(ProductColor productColor) {
		this.productColor = productColor;
	}
	public Long getNrOfPiecesInStock() {
		return nrOfPiecesInStock;
	}
	public void setNrOfPiecesInStock(Long nrOfPiecesInStock) {
		this.nrOfPiecesInStock = nrOfPiecesInStock;
	}
	
	
}
