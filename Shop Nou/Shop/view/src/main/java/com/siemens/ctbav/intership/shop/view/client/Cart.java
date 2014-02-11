package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	public void initialize() {
		cart = new HashMap<ProductColorSize, Integer>();
	}

	public int nrOfProducts() {
		return cart.size();
	}

	public double totalPrice() {
		double s = 0.0;

		Iterator<Map.Entry<ProductColorSize, Integer>> it = cart.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Integer> pair = it.next();
			s += pair.getValue() * pair.getKey().getProductcolor().getProduct().getPrice();
		}

		return s;
	}

	public List<Entry<ProductColorSize, Integer>> getProducts() {
		return new ArrayList<Entry<ProductColorSize, Integer>>(cart.entrySet());
	}
	
	public void addProduct(ProductColorSize productToAdd,Integer numberOfProducts){
		cart.put(productToAdd, numberOfProducts);
		System.out.println("s-au adaugat "+numberOfProducts);
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
