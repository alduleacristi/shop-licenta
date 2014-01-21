package com.siemens.ctbav.intership.shop.dto.superadmin;

public class SizeDTO {
	private String size;
	private CategoryDTO category;

	public SizeDTO(String size, CategoryDTO category) {
		this.size = size;
		this.category = category;
	}

	public SizeDTO() {
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		SizeDTO other = (SizeDTO) obj;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equalsIgnoreCase(other.size))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SizeDTO [size=" + size + ", category=" + category + "]";
	}

}
