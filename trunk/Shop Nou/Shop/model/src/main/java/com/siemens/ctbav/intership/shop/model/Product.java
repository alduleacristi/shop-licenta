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

@NamedQueries({ @NamedQuery(name = Product.GET_PRODUCTS_BY_CATEGORY, query = "SELECT p FROM Product p where p.category.id = :id") })
@NamedNativeQueries({ @NamedNativeQuery(name = Product.GET_GENERIC_PRODUCTS_FROM_CATEGORY, query = "CALL generic_products_child_categories(:param)", resultClass = Product.class) })
@Entity
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012681729962896743L;

	public static final String GET_PRODUCTS_BY_CATEGORY = "getProductsByCategory";
	public final static String GET_GENERIC_PRODUCTS_FROM_CATEGORY = "getGenericProductsFromCategory";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
