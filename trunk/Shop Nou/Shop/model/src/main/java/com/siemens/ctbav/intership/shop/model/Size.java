package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = Size.GET_ALL_SIZES, query = "SELECT s FROM Size s"),
		@NamedQuery(name = Size.GET_SIZES_BY_CATEGORY, query = "SELECT s FROM Size s where s.category.id = :idCategory") })
@NamedNativeQueries({ @NamedNativeQuery(name = Size.CALL_SIZES_PARENT_CATEGORIES, query = "CALL sizes_parent_categories(:param)", resultClass = Size.class) })
public class Size implements Serializable {

	private static final long serialVersionUID = 2113861263129743887L;

	public static final String GET_ALL_SIZES = "getAllSizes";
	public static final String GET_SIZES_BY_CATEGORY = "getSizesByCategory";
	public static final String CALL_SIZES_PARENT_CATEGORIES = "callSizesParentCategories";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_size")
	private Long id;

	@Column(name = "name")
	private String size;

	@ManyToOne
	@JoinColumn(name = "id_cat")
	private Category category;

	public Size() {
	}

	public Size(String size, Category category) {
		this.size = size;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSize() {
		return size;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "Size [id_size=" + id + ", size=" + size + ", category="
				+ category + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Size other = (Size) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}

}
