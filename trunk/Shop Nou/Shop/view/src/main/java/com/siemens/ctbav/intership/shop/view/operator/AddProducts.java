package com.siemens.ctbav.intership.shop.view.operator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.service.operator.ProductService;

@ManagedBean(name = "addProducts")
@RequestScoped
public class AddProducts {

	private List<MissingProduct> productList;
	
	@EJB
	private ProductService prodService;
	
	@PostConstruct
	public void postConstruct() {
		productList = new ArrayList<MissingProduct>();
		//in productService calculez pt fiecare produs diferenta dintre cate sunt pe stoc si cate s-au cerut in comenzi
	
	}


	public List<MissingProduct> getProductList() {
		return productList;
	}

	public void add(MissingProduct product) {
		if (product.getPiecesAdded() <= 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"The number of pieces must be greater than 0", ""));
			return;
		}
		
	//	product.getProduct().setNrPieces(product.getNrPieces() + product.getPiecesAdded());
		try {
			prodService.addProducts(product.getProduct());
		} catch (ProductException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage(), ""));
		}
	}

}
