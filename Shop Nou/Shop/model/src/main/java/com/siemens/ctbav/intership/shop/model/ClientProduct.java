package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQuery(name=ClientProduct.GET_PRODUCTS_FROM_COMMAND, query="SELECT c FROM ClientProduct c")
@Table(name = "client_product")
public class ClientProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_PRODUCTS_FROM_COMMAND = "getProductsFromCommand";
	
	@Id
	@Column(name = "id_client_product")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProductClient;

	@Column(name = "nr_pieces")
	private Long nrPieces;

	@Column(name = "perc_reduction")
	private Double percRedution;

	private Double price;

	@OneToOne
	@JoinColumn(name="id_pcs")
	private ProductColorSize product;

	public ClientProduct() {
	}

	public Long getIdProductClient() {
		return this.idProductClient;
	}

	public void setIdProductClient(Long idProductClient) {
		this.idProductClient = idProductClient;
	}

	public Long getNrPieces() {
		return this.nrPieces;
	}

	public void setNrPieces(Long nrPieces) {
		this.nrPieces = nrPieces;
	}

	public Double getPercRedution() {
		return this.percRedution;
	}

	public void setPercRedution(Double percRedution) {
		this.percRedution = percRedution;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ProductColorSize getProduct() {
		return this.product;
	}

	public void setProduct(ProductColorSize product) {
		this.product = product;
	}
}