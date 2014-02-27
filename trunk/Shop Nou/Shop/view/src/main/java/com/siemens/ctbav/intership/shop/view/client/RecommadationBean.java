package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.client.ProductService;
import com.siemens.ctbav.intership.shop.service.client.RecommandService;

@ManagedBean(name = "recommandationClient")
@SessionScoped
@URLMapping(id = "recommandationClient" , pattern = "/client/user/recommandation/#{recommandationClient.id}", viewId = "/client/user/recommandation.xhtml")
public class RecommadationBean {
	@ManagedProperty(value = "#{id}")
	private long id;
	
	@EJB
	private RecommandService recommandService;
	
	@EJB
	private ProductService productService;

	private List<Product> recommandedProducts;
	
	@PostConstruct
	private void initialize(){
		recommandedProducts = new ArrayList<Product>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {		
		if(id != 0)
			populateRecomandedProducts(id);
		this.id = id;
	}

	public List<Product> getRecommandedProducts() {
		return recommandedProducts;
	}

	public void setRecommandedProducts(List<Product> recommandedProducts) {
		this.recommandedProducts = recommandedProducts;
	}
	
	private void populateRecomandedProducts(Long id){
		List<Long> recomandedProductsId = recommandService.getRecommandation(id);
		recommandedProducts.clear();
		
		for(Long idProduct:recomandedProductsId){
			try {
				Product p = productService.getProductById(idProduct);
				recommandedProducts.add(p);
			} catch (ProductDoesNotExistException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isEmpty(){
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
}
