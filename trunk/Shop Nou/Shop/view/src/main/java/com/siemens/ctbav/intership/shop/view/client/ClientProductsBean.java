package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;

@ManagedBean(name = "clientProducts")
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "id6", pattern = "/client/user/products/#{clientProducts.id}", viewId = "/client/user/productDescription.xhtml") })
public class ClientProductsBean {

	@ManagedProperty(value = "#{id}")
	private long id;

	@EJB
	private ProductColorService productColorService;

	private ProductColor selectedProduct;
	private Category selectedCategory;
	private List<ProductColor> productList;

	@PostConstruct
	void postConstruct() {
		productList = new ArrayList<ProductColor>();

		if (selectedCategory == null) {
			// System.out.println("iau categoria din sessionMap");
			setSelectedCategory((Category) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("category"));
		}

		productList.clear();

		if (selectedCategory != null) {
			productList = productColorService
					.getProductsByCategory(selectedCategory.getId());
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		// System.out
		// .println("se seteaza id-ul pt mapare in client products" + id);
		this.id = id;
		if (id != 0) {
			try {
				selectedProduct = productColorService.getProductById(id);
			} catch (ProductDoesNotExistException e) {
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"Sorry. You can't acces products now."));
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("myObj", selectedProduct);
		}
	}

	public ProductColor getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(ProductColor selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<ProductColor> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductColor> productList) {
		this.productList = productList;
	}
	

	private void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProduct(ProductColor product) {
		// System.out.println("SHOW PRODUCT!!!!");
		// System.out.println(product.getIdProduct());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("myObj", product);
		// System.out.println("Id-ul produsului este: " +
		// product.getProduct().getName());
		setId(product.getProduct().getId());
		redirect("/Shop4j/client/user/products/" + product.getProduct().getId());
	}

}
