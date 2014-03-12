package com.siemens.ctbav.intership.shop.dto.operator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CommandDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long id;
	private Date orderDate, deliveryDate;
	private AdressDTO adress;
	private ClientDTO client;
	private CommandStatusDTO status;
	private Long id_operator;
	private List<ClientProductDTO> listProducts;
	
	
	public CommandDTO(Long id, Date orderDate, Date deliveryDate,
			AdressDTO adress, ClientDTO client, CommandStatusDTO status,
			List<ClientProductDTO> listProducts) {
		this.id = id;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.adress = adress;
		this.client = client;
		this.status = status;
		this.setListProducts(listProducts);
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public AdressDTO getAdress() {
		return adress;
	}
	public ClientDTO getClient() {
		return client;
	}
	
	public CommandStatusDTO getStatus() {
		return status;
	}
	public void setStatus(CommandStatusDTO status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	@Override
	public String toString() {
		return "CommandDTO [orderDate=" + orderDate + ", deliveryDate="
				+ deliveryDate + ", adress=" + adress + ", client=" + client
				+ ", status=" + status + "]";
	}
	public List<ClientProductDTO> getListProducts() {
		return listProducts;
	}
	public void setListProducts(List<ClientProductDTO> listProducts) {
		this.listProducts = listProducts;
	}
	public Long getId_operator() {
		return id_operator;
	}
	public void setId_operator(Long id_operator) {
		this.id_operator = id_operator;
	}
	
	
}
