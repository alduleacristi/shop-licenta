package com.siemens.ctbav.intership.shop.dto.superadmin.importer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDto {
	private String name;
	private String description;
	private String price;
	private String percReduction;
	private String category;
	private String color;
	private String size;
	private String nrPieces;

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPercReduction() {
		return percReduction;
	}

	public void setPercReduction(String reduction) {
		this.percReduction = reduction;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(String nrPieces) {
		this.nrPieces = nrPieces;
	}

	@Override
	public String toString() {
		return "ProductDto [name=" + name + ", description=" + description
				+ ", price=" + price + ", percReduction=" + percReduction
				+ ", category=" + category + ", color=" + color + ", size="
				+ size + ", nrPieces=" + nrPieces + "]";
	}
}
