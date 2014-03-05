package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.client.SearchService;

@SessionScoped
@ManagedBean(name = "searchClient")
@URLMapping(id = "searchClient", pattern = "/client/user/Search", viewId = "/client/user/search.xhtml")
public class SearchBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5001018970672831617L;

	@EJB
	private SearchService searchService;

	private String query;

	private int minPrice = 0, maxPrice = 80;

	private List<Product> products;
	
	private boolean noProducts;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		System.out.println("in set query: " + query);
		this.query = query;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean getNoProducts() {
		return noProducts;
	}

	public void setNoProducts(boolean noProducts) {
		this.noProducts = noProducts;
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

	public void search(String query) {
		if (query.length() < 3)
			return;

		String lowerQuery = query.toLowerCase();

		products = searchService.search(lowerQuery);
		
		for(int i=0;i<products.size();i++)
			if(products.get(i).getPrice() < minPrice || products.get(i).getPrice() > maxPrice){
				products.remove(i);
				i--;
			}

		if (products.size() == 0)
			setNoProducts(false);
		else
			setNoProducts(true);

//		for (Product p : products)
//			System.out.println(p);

		redirect("/Shop4j/client/user/Search");
	}

	public List<String> complete(String str) {
		String lowerQuery = str.toLowerCase();
		List<String> names = searchService.searchForAutoComplete(lowerQuery);

		for (String s : names)
			if (!names.contains(s)) {
				names.add(s);
				//System.out.println(s);
			}

		return names;
	}
	
	public void chooseAProduct(Product product) {
		redirect("/Shop4j/client/user/productSearchDescription/"
				+ product.getId());
	}
}
