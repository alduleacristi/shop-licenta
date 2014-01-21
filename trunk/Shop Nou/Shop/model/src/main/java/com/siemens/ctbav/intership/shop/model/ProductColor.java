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
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity(name="product_color")
@NamedNativeQueries({
	@NamedNativeQuery(name = ProductColor.GET_PRODUCTS_FROM_CATEGORY, query = "CALL products_child_categories(:param)", resultClass = ProductColor.class)})
public class ProductColor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8367516895914463732L;
	
	public final static String GET_PRODUCTS_FROM_CATEGORY = "get_product_from_category";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_prod_col;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	
	@ManyToOne
	@JoinColumn(name = "id_color")
	private Color color;
	
	@OneToMany(mappedBy = "productcolor" , fetch = FetchType.EAGER)
	List<ProductColorSize> productColorSize;

	public Product getProduct() {
		return product;
	}

	public Color getColor() {
		return color;
	}

	public Long getId_prod_col() {
		return id_prod_col;
	}

	public void setId_prod_col(Long id_prod_col) {
		this.id_prod_col = id_prod_col;
	}

	public List<ProductColorSize> getProductColorSize() {
		return productColorSize;
	}

	public void setProductColorSize(List<ProductColorSize> productColorSize) {
		this.productColorSize = productColorSize;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	
}
