package com.siemens.ctbav.intership.shop.view.operator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertReturnedOrders;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertReturnedProduct;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.model.ReturnedProducts;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;

@RequestScoped
@ManagedBean(name = "returned")
public class ReturnedOrders {

	@EJB
	private CommandService commService;

	private List<ReturnedOrdersDTO> returnedOrders;

	@PostConstruct
	public void postConstruct() {
		try {
			setReturnedOrders(ConvertReturnedOrders
					.convertListOfReturnedOrders(commService
							.getReturnedOrders()));
		} catch (CommandNotFoundException exc) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, exc
							.getMessage(), exc.getMessage()));
		}
	}

	public List<ReturnedOrdersDTO> getReturnedOrders() {
		System.out.println(returnedOrders.size());
		return returnedOrders;
	}

	public void setReturnedOrders(List<ReturnedOrdersDTO> returnedOrders) {
		this.returnedOrders = returnedOrders;
	}

	public void returnOrder(ReturnedOrdersDTO order) {

	}

	
}
