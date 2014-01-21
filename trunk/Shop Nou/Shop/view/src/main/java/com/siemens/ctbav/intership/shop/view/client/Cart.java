package com.siemens.ctbav.intership.shop.view.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.Product;

@ManagedBean(name="cart")
@SessionScoped
@URLMappings(
		mappings = {
				@URLMapping(id = "cart" , pattern = "/client/Cart" , viewId = "Cart.xhtml"),
		}
	)

public class Cart {
	private Map<Product, Integer> cart;
	
//	@EJB
//	ProductService productService;
	
	private boolean visible;
	
	public boolean isVisible() {
		System.out.println("!!!!!!!!!In isVisible:" + this.visible);
		return this.visible;
	}

	public void setVisible(boolean visible) {
		System.out.println("!!!!!!!!!!Setvisible = true");
		this.visible = visible;
	}

	@PostConstruct
	public void initialize(){
		this.setVisible(false);
		cart = new HashMap<Product, Integer>();
		
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!Post construct cart!!!!!");
		//List<Product> products = productService.getProducts();
		
		//for(Product p:products)
			//cart.put(p, 1);
		
		//List<Entry<Product, Integer>> productsFromMap = getProducts();
		//for(Entry<Product, Integer> p:productsFromMap)
			//System.out.println(p.getKey().getPrice());
	}
	
	public int getNrOfProducts(boolean logged){
		if(!logged)
			return 0;
		return cart.size();
	}
	
	public double getTotalPrice(boolean logged){
		if(!logged)
			return 0.0;
		
		double s = 0.0;
		
		Iterator<Map.Entry<Product, Integer>> it = cart.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Product, Integer> pair = it.next();
			s += pair.getValue()*pair.getKey().getPrice();
		}
		
		return s;
	}
	
	public List<Entry<Product,Integer>> getProducts(){
		return new ArrayList<Entry<Product,Integer>>(cart.entrySet());
	}
	
	public void removeProduct(Product product){
		if(cart.containsKey(product)){
			cart.remove(product);
			try {
				setVisible(false);
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("/Shop4J/client/Cart");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
