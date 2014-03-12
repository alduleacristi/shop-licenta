package com.siemens.ctbav.intership.shop.jaxb.operator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;


@XmlRootElement(name="orders")
public class OrderJAXB {

	//aici o sa am campurile clasei order, campurile pe care vreau sa le salvez in fisier
	
	private Date orderDate, deliveryDate;
	private AdressJAXB adress;
	private ClientJAXB client;
	private String status;
	private Long idOperator;
	private List<ClientProductJAXB> productList;
	
	
	public OrderJAXB(){
		
	}
	
	public OrderJAXB(CommandDTO order){
		this.orderDate=order.getOrderDate();
		this.deliveryDate=order.getDeliveryDate();
		this.adress =  new AdressJAXB(order.getAdress());
		this.client = new ClientJAXB(order.getClient());
		this.status = order.getStatus().getStatus();
		this.idOperator=order.getId_operator();
		
		List<ClientProductJAXB> list = new ArrayList<ClientProductJAXB>();
		for(ClientProductDTO cl : order.getListProducts())
			list.add(new ClientProductJAXB(cl));
		
		this.productList = list;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	@XmlElement
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	@XmlElement
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public AdressJAXB getAdress() {
		return adress;
	}

	@XmlElement
	public void setAdress(AdressJAXB adress) {
		this.adress = adress;
	}

	public ClientJAXB getClient() {
		return client;
	}

	@XmlElement
	public void setClient(ClientJAXB client) {
		this.client = client;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}

	public Long getIdOperator() {
		return idOperator;
	}

	@XmlElement
	public void setIdOperator(Long idOperator) {
		this.idOperator = idOperator;
	}

	public List<ClientProductJAXB> getProductList() {
		return productList;
	}

	@XmlElementWrapper
	public void setProductList(List<ClientProductJAXB> productList) {
		this.productList = productList;
	}
	
	
}
