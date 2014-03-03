package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * The persistent class for the Category database table.
 * 
 */
@NamedNativeQueries({
		@NamedNativeQuery(name = Category.GET_PARENTS_CATEGORY, query = "CALL parent_categories(:param)", resultClass = Category.class),
		@NamedNativeQuery(name = Category.GET_CHILDREN_CATEGORY, query = "CALL child_categories(:param)", resultClass = Category.class) })
@Entity
@NamedQueries({ @NamedQuery(name = Category.GET_ALL_CATEGORIES, query = "SELECT c FROM Category c") })
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_ALL_CATEGORIES = "getAllCategories";
	public static final String GET_PARENTS_CATEGORY = "callParentsCategory";
	public static final String GET_CHILDREN_CATEGORY = "callChildrenCategory";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "id_parent")
	private Category category;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private List<Category> categories;

	public Category() {
	}

	public Category(String name, Category category) {
		this.name = name;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIerarhie() {
		StringBuilder sb = new StringBuilder(100);
		for (Category c : categories)
			sb.append(c.name + " ");
		sb.append(name);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		Category parent1 = other.getCategory();
		Category parent2 = category;
		if (name != null && other.name != null) {
			if (name.equals(other.name)) {
				if (parent1 != null && parent2 == null)
					return false;
				if (parent1 == null && parent2 != null)
					return false;
				if (parent1 != null && parent2 != null)
					if (!parent1.getName().equals(parent2.getName()))
						return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}