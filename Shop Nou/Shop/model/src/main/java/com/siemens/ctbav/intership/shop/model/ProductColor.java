package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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

@Entity(name = "product_color")
@NamedQueries({ @NamedQuery(name = ProductColor.GET_COLOR_PRODUCTS_FOR_PRODUCT, query = "SELECT p FROM product_color p where p.product.id = :id") })
@NamedNativeQueries({ @NamedNativeQuery(name = ProductColor.GET_PRODUCTS_FROM_CATEGORY, query = "CALL products_child_categories(:param)", resultClass = ProductColor.class) })
public class ProductColor implements Serializable {

	private static final long serialVersionUID = -8367516895914463732L;

	public final static String GET_PRODUCTS_FROM_CATEGORY = "get_product_from_category";
	public final static String GET_COLOR_PRODUCTS_FOR_PRODUCT = "getColorProductsForProduct";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prod_col")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "id_color")
	private Color color;

	@OneToMany(mappedBy = "productcolor", fetch = FetchType.EAGER)
	List<ProductColorSize> productColorSize;

	public ProductColor() {
	}

	public ProductColor(Product p, Color c) {
		this.product = p;
		this.color = c;
	}

	public Product getProduct() {
		return product;
	}

	public Color getColor() {
		return color;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "ProductColor [id=" + id + ", product=" + product + ", color="
				+ color + ", productColorSize=" + productColorSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime
				* result
				+ ((productColorSize == null) ? 0 : productColorSize.hashCode());
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
		ProductColor other = (ProductColor) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productColorSize == null) {
			if (other.productColorSize != null)
				return false;
		} else if (!productColorSize.equals(other.productColorSize))
			return false;
		return true;
	}

}
