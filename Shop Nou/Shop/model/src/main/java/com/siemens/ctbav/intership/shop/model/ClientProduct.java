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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQueries({
		@NamedQuery(name = ClientProduct.GET_PRODUCTS_FROM_COMMAND, query = "SELECT c FROM ClientProduct c"),
		@NamedQuery(name = ClientProduct.GET_CLIENT_PRODUCTS_FOR_PRODUCTS, query = "select c from ClientProduct c where c.product.id=:id") })
@Table(name = "client_product")
public class ClientProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_PRODUCTS_FROM_COMMAND = "getProductsFromCommand";
	public static final String GET_CLIENT_PRODUCTS_FOR_PRODUCTS = "getclientproducts";
	@Id
	@Column(name = "id_client_product")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProductClient;

	@Column(name = "nr_pieces")
	private Long nrPieces;

	@Column(name = "perc_reduction")
	private Double percRedution;

	@Column(name = "price")
	private Double price;

	@OneToOne
	@JoinColumn(name = "id_pcs")
	private ProductColorSize product;

	@ManyToOne
	@JoinColumn(name = "id_command")
	private Command command;

	public Command getCommand() {
		return command;
	}

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

	public void setCommand(Command command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "ClientProduct [nrPieces=" + nrPieces + ", percRedution="
				+ percRedution + ", price=" + price + ", product=" + product
				+ ", command=" + command + "]";
	}
	
	
}