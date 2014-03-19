package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;
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

	@EJB
	private ProductColorService productColorService;

	@EJB
	private ConfigurationService confService;

	private List<Product> products;
	private String host;

	@PostConstruct
	private void initialize() {
		String msg = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("commandSent");
		if (msg != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg));
		}

		this.host = confService.getHost();

		products = new ArrayList<Product>();
		try {
			products = productService.getReducedProducts();

			if (products.size() < 6)
				throw new ProductDoesNotExistException(
						"There are not enough products reduced");
		} catch (ProductDoesNotExistException e) {
			if (products.size() < 6) {
				try {
					List<Product> randProducts = productService
							.getProductsRandom(6 - products.size());

					for (Product p : randProducts)
						if (!products.contains(p))
							products.add(p);
				} catch (ProductDoesNotExistException e1) {
				}
			}

		}
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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

	public String getProductColorId(Product p) {
		List<ProductColor> productsColor = productColorService
				.getColorsForProduct(p.getId());
		if (productsColor.size() > 0)
			return productsColor.get(0).getId().toString();
		return "-1";
	}

	public void chooseAProduct(Product product) {
		redirect("/Shop4j/client/user/productReduceDescription/"
				+ product.getId());
	}
}
