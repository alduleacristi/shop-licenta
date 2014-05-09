package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;
import com.siemens.ctbav.intership.shop.service.client.SearchService;

@SessionScoped
@ManagedBean(name = "searchVisitor")
@URLMapping(id = "searchVisitor", pattern = "/client/Search", viewId = "/client/search.xhtml")
public class SearchBeanVisitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5001018970672831617L;

	@EJB
	protected SearchService searchService;

	@EJB
	protected ProductColorService productColorService;

	@EJB
	protected ConfigurationService confService;

	private String query, host;

	protected int minPrice = 0, maxPrice = 80;

	protected List<Product> products;

	private boolean noProducts;

	@PostConstruct
	private void initialize() {
		this.host = confService.getHost();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

		for (int i = 0; i < products.size(); i++)
			if (products.get(i).getPrice() < minPrice
					|| products.get(i).getPrice() > maxPrice) {
				products.remove(i);
				i--;
			}

		System.out.println(products.size());
		if (products.size() == 0) {
			setNoProducts(false);
			System.out.println("lista e goala");
		}

		else {
			setNoProducts(true);
			System.out.println("lista nu e goala");
		}

		redirect("/Shop4j/client/Search");
	}

	public List<String> complete(String str) {

		String lowerQuery = str.toLowerCase();
		List<String> names = searchService.searchForAutoComplete(lowerQuery);

		for (String s : names)
			if (!names.contains(s)) {
				names.add(s);
			}

		return names;
	}

	public void chooseAProduct(Product product) {
		String url = "/Shop4j/client/user/productSearchDescription/"
				+ product.getId();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("urlProduct", url);

		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("client") != null) {
			redirect("/Shop4j/client/user/productSearchDescription/"
					+ product.getId());
		} else {
			redirect("/Shop4j/client/productSearchDescription/"
					+ product.getId());
		}
	}

	public String getProductColorId(Product p) {
		List<ProductColor> productsColor = productColorService
				.getColorsForProduct(p.getId());
		if (productsColor.size() > 0)
			return productsColor.get(0).getId().toString();
		return "-1";
	}
}
