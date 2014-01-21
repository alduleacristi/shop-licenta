package com.siemens.ctbav.intership.shop.dto.superadmin;

import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertCategory;
import com.siemens.ctbav.intership.shop.model.Category;

public class ProductDTO {

	private String name;
	private String description;
	private Double price;
	private CategoryDTO category;

	public ProductDTO() {
	}

	public ProductDTO(String name, String description, Double price,
			Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = ConvertCategory.convertCategory(category);
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

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category.getName() == null) ? 0 : category.getName().hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		ProductDTO other = (ProductDTO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.getName().equals(other.category.getName()))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO [name=" + name + ", description=" + description
				+ ", price=" + price + ", category=" + category + "]";
	}
}
