package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.client.ProductService;

@ManagedBean(name = "reductionProductsClient")
@ViewScoped
public class ReductionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1709502740301361231L;

	@EJB
	private ProductService productService;

	private List<Product> products;

	@PostConstruct
	private void initialize() {
		products = productService.getReducedProducts();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	private void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Sorry.Description is not availabel."));
		}
	}

	public void chooseAProduct(Product product) {
		redirect("/Shop4j/client/user/productReduceDescription/"
				+ product.getId());
	}
}
