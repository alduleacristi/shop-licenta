package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

@ManagedBean(name = "cart")
@SessionScoped
@URLMappings(mappings = { @URLMapping(id = "cartClient", pattern = "/client/user/Cart", viewId = "/client/user/cart.xhtml"), })
public class Cart {
	private Map<ProductColorSize, Integer> cart;

	// @EJB
	// ProductService productService;

	private Integer nrOfProducts;

	private Double totalPrice;
	
	private List<ProductColorSize> products;

	@PostConstruct
	public void initialize() {
		cart = new HashMap<ProductColorSize, Integer>();
		setNrOfProducts(0);
		setTotalPrice(0.0);
		products = new ArrayList<ProductColorSize>();
	}

	public Integer getNrOfProducts() {
		return nrOfProducts;
	}

	public void setNrOfProducts(Integer nrOfProducts) {
		this.nrOfProducts = nrOfProducts;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProducts() {
		System.out.println("in set products");
		products.clear();
		Set<ProductColorSize> list = cart.keySet();
		for (ProductColorSize pcs : list)
			products.add(pcs);
	}

	public List<ProductColorSize> getProducts() {
		return products;
	}
	
	public String getNumberOfProducts(ProductColorSize pcs){
		System.out.println("in number of products "+cart.get(pcs));
		return String.valueOf(cart.get(pcs));
	}

	public void addProduct(ProductColorSize pcs, Integer nrOfProducts) {
		if (pcs == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"You must choose a size"));
			return;
		}
		if (nrOfProducts == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"You must enter the number of products"));
			return;
		}
		
		if (nrOfProducts > pcs.getNrOfPieces()) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"We dont't have enough product in stock"));
			return;
		}

		if (cart.containsKey(pcs)) {
		//	System.out.println("exista deja");
			cart.put(pcs, cart.get(pcs) + nrOfProducts);
			setTotalPrice(getTotalPrice() + nrOfProducts * pcs.getProductcolor().getProduct().getPrice());
		} else {
			cart.put(pcs, nrOfProducts);
			setNrOfProducts(getNrOfProducts() + 1);
			setTotalPrice(getTotalPrice() + nrOfProducts * pcs.getProductcolor().getProduct().getPrice());
		}
		
		setProducts();
	}

	public void removeProduct(Product product) {
		if (cart.containsKey(product)) {
			cart.remove(product);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/Shop4J/client/Cart");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
