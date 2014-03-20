package com.siemens.ctbav.intership.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name = ReturnedProducts.getReturnedProductsForOrder, query = "SELECT p from ReturnedProducts p where p.command.command.id=:id"), 
	})

public class ReturnedProducts {

	public final static String getReturnedProductsForOrder="getReturnedProductsForOrder";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_command")
	private ReturnedOrders command;

	@OneToOne
	@JoinColumn(name = "id_product")
	private ProductColorSize product;

	public ReturnedProducts(ReturnedOrders command, ProductColorSize product) {
		this.command = command;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public ReturnedOrders getCommand() {
		return command;
	}

	public void setCommand(ReturnedOrders command) {
		this.command = command;
	}

	public ProductColorSize getProduct() {
		return product;
	}

	public void setProduct(ProductColorSize product) {
		this.product = product;
	}

}