package com.siemens.ctbav.intership.shop.view.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.dto.operator.MissingProduct;
import com.siemens.ctbav.intership.shop.exception.operator.ProductException;
import com.siemens.ctbav.intership.shop.service.operator.ProductService;

@ManagedBean(name = "addProducts")
@RequestScoped
public class AddProducts {

	private Set<MissingProduct> productSet;
	private List<MissingProduct> productList;

	@EJB
	private ProductService prodService;

	@PostConstruct
	public void postConstruct() {
		productSet = new TreeSet<MissingProduct>();
		productList = new ArrayList<MissingProduct>();
		// in productService calculez pt fiecare produs diferenta dintre cate
		// sunt pe stoc si cate s-au cerut in comenzi
		try {
			productSet = prodService.getMissingProducts();
			productList.addAll(productSet);
		} catch (ProductException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));
		}
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
		try {
			prodService.addProducts(product);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							product.getPiecesAdded()+" products were successfully added!", ""));
		} catch (ProductException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

}
