package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.exception.client.NullRecommander;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.client.ProductService;
import com.siemens.ctbav.intership.shop.service.client.RecommandService;

@ManagedBean(name = "recommandationClient")
@ViewScoped
@URLMapping(id = "recommandationClient", pattern = "/client/user/recommandation", viewId = "/client/user/recommandation.xhtml")
public class RecommadationBean {
	@EJB
	private RecommandService recommandService;

	@EJB
	private ProductService productService;

	@EJB
	private ConfigurationService confService;

	private List<Product> recommandedProducts;

	private String message, host;

	@PostConstruct
	private void initialize() {
		this.host = confService.getHost();

		message = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("addProductMessage");

		if (message != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().remove("addProductMessage");

			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Succes", message));
		}

		recommandedProducts = new ArrayList<Product>();

		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		populateRecomandedProducts(client.getId());
	}

	public List<Product> getRecommandedProducts() {
		return recommandedProducts;
	}

	public void setRecommandedProducts(List<Product> recommandedProducts) {
		this.recommandedProducts = recommandedProducts;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	private void populateRecomandedProducts(Long id) {
		List<Long> recomandedProductsId;
		try {
			recomandedProductsId = recommandService.getRecommandation(id);

			recommandedProducts.clear();

			for (Long idProduct : recomandedProductsId) {
				try {
					Product p = productService.getProductById(idProduct);
					recommandedProducts.add(p);
				} catch (ProductDoesNotExistException e) {
					e.printStackTrace();
				}
			}
		} catch (NullRecommander e1) {
			recommandedProducts.clear();
		}

	}

	public boolean isEmpty() {
		if (recommandedProducts.size() == 0)
			return false;

		return true;
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
		redirect("/Shop4j/client/user/productRecommandedDescription/"
				+ product.getId());
	}
	
	public void addProduct(){
		Product product;
		int k=1;
		
		for(int i=0;i<1500;i++){
			product = new Product();
			product.setName("Product"+k++);
			product.setPrice(100.0);
			product.setReduction(0.0);
			productService.addProduct(product,4L);
		}
	}
}
