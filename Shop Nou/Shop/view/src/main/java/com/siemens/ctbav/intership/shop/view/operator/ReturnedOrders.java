package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertReturnedOrders;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertReturnedProduct;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
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
	private List<ReturnedProductsDTO> returnedProducts;
	private List<Long> pieces;
	//private Iterator it;

	@PostConstruct
	public void postConstruct() {
		System.out.println("in post construct");
	//	setPieces(new ArrayList<Long>());
	//	returnedProducts = new ArrayList<ReturnedProductsDTO>();
		try { 
			setReturnedOrders(ConvertReturnedOrders
					.convertListOfReturnedOrders(commService
							.getReturnedOrders()));
		} catch (CommandNotFoundException exc) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, exc
							.getMessage(), exc.getMessage()));
		}
	}

	public List<Long> getPieces() {
		return pieces;
	}

	private void setPieces(List<Long> pieces) {
		this.pieces = pieces;
	}

	public List<ReturnedOrdersDTO> getReturnedOrders() {
		return returnedOrders;
	}

	public void setReturnedOrders(List<ReturnedOrdersDTO> returnedOrders) {
		this.returnedOrders = returnedOrders;
	}

	public List<ReturnedProductsDTO> productList(ReturnedOrdersDTO order) {

//		for (ClientProductDTO cp : order.getCommand().getListProducts())
//			pieces.add(cp.getNrPieces());
//		it = pieces.iterator();
//		returnedProducts = ConvertReturnedProduct.convertList((commService
//				.getReturnedProductsForOrder(order)));
//		return returnedProducts;
		
//		for (ClientProductDTO cp : order.getCommand().getListProducts())
//			pieces.add(cp.getNrPieces());
	//	it = pieces.iterator();
		Long idOrder = order.getId();
		returnedProducts=ConvertReturnedProduct.convertList(commService.getProductsReturned(idOrder));
		return returnedProducts;
		
	}

	public void returnOrder(ReturnedOrdersDTO order) {

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		// adaug nr de bucati in baza
		
		List<ProductColorSizeDTO> excProducts = updateStock(order);
		StringBuilder message = new StringBuilder("The stock has been updated");
		StringBuilder names = new StringBuilder();
		// daca nu s-a adaugat nr de bucati pt toate produsele
		if (excProducts.size() != 0) {
			names.append("The following products couldn't be added:"
					+ System.getProperty("line.separator"));
			for (ProductColorSizeDTO pr : excProducts)
				names.append(pr.toString()
						+ System.getProperty("line.separator"));

		} else {
			commService.setAsReturned(order);
		}
		if (names.length() != 0)
			message = names;
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						message.toString(), ""));
		try {
			context.redirect("returnedOrders.xhtml");
		} catch (IOException e) {

		}
	}

	private List<ProductColorSizeDTO> updateStock(ReturnedOrdersDTO order) {
		List<ProductColorSizeDTO> excProducts = new ArrayList<ProductColorSizeDTO>();
		for (ClientProductDTO cp : order.getCommand().getListProducts()) {
			ProductColorSizeDTO prod = cp.getProduct();
			if (isReturned(prod)) {
				try {
				//	System.out.println(prod+ " " + cp.getNrPieces());
					commService.addReturnedProductsPieces(cp);
				} catch (ProductDoesNotExistException e) {
					excProducts.add(prod);
				}
			}
		}

		return excProducts;
	}

	private boolean isReturned(ProductColorSizeDTO prod) {
		for (ReturnedProductsDTO retProd : returnedProducts) {
			ProductColorSizeDTO pr = retProd.getProduct();
			if (pr.equals(prod))
				return true;
		}
		return false;
	}

	public void retreiveOrder(ReturnedOrdersDTO order) {
		try {
			commService.setAsRetreived(order);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.getFlash().setKeepMessages(true);
			context.redirect("returnedOrders.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	public long piecesForProduct() {
		
//		if (it!= null && it.hasNext())
//			return (Long) it.next();
		return 0;
	}

}
