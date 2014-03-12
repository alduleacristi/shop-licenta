package com.siemens.ctbav.intership.shop.dto.superadmin.exporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.model.ProductColorSize;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "products")
public class ProductDto {
	@XmlElement(name = "name", required = true)
	private String name;

	@XmlElement(name = "description", required = true)
	private String description;

	@XmlElement(name = "price", required = true)
	private Double price;

	@XmlElement(name = "percReduction", required = true)
	private Double percReduction;

	@XmlElement(name = "category", required = true)
	private Long category;

	@XmlElement(name = "color", required = true)
	private Long color;

	@XmlElement(name = "size", required = true)
	private Long size;

	@XmlElement(name = "nrPieces", required = true)
	private Long nrPieces;

	public ProductDto() {
	}

	public ProductDto(ProductColorSize pcs) {
		this.name = pcs.getProductcolor().getProduct().getName();
		this.description = pcs.getProductcolor().getProduct().getDescription();
		this.price = pcs.getProductcolor().getProduct().getPrice();
		this.percReduction = pcs.getProductcolor().getProduct().getReduction();
		this.category = pcs.getProductcolor().getProduct().getCategory()
				.getId();
		this.color = pcs.getProductcolor().getColor().getId();
		this.size = pcs.getSize().getId();
		this.nrPieces = pcs.getNrOfPieces();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPercReduction() {
		return percReduction;
	}

	public void setPercReduction(Double percReduction) {
		this.percReduction = percReduction;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getColor() {
		return color;
	}

	public void setColor(Long color) {
		this.color = color;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}
}
