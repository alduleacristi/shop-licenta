package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

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

import com.siemens.ctbav.intership.shop.model.Size;


@Entity(name = "product_color_size")
@NamedQueries({ @NamedQuery(name = ProductColorSize.GET_PRODUCT_COLOR_SIZE_BY_ID, query = "SELECT p FROM product_color_size p where p.id = :id") })
public class ProductColorSize implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8254096206057865195L;
	
	public static final String GET_PRODUCT_COLOR_SIZE_BY_ID = "getColorProductSizeById";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pcs")
	private Long id;
	
	@Column(name = "nr_pieces")
	Long nrOfPieces;
	
	//join productColor
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_prod_col")
	private ProductColor productcolor;
	
	//join Size
	@ManyToOne
	@JoinColumn(name="id_size")
	private Size size;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNrOfPieces() {
		return nrOfPieces;
	}

	public void setNrOfPieces(Long nrOfPieces) {
		this.nrOfPieces = nrOfPieces;
	}

	public ProductColor getProductcolor() {
		return productcolor;
	}

	public void setProductcolor(ProductColor productcolor) {
		this.productcolor = productcolor;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

}
