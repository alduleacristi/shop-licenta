package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
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
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;

@ManagedBean(name = "clientProducts")
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "id6", pattern = "/client/user/products/#{clientProducts.id}", viewId = "/client/user/productDescription.xhtml") })
public class ClientProductsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6579965298560785014L;

	@ManagedProperty(value = "#{id}")
	private long id;

	@EJB
	private ProductColorService productColorService;

	private ProductColor selectedProduct;
	private Category selectedCategory;
	private List<ProductColor> productList;
	private String availabel;
	private Boolean isAvailabel;
	private ProductColorSize productColorSize;

	@PostConstruct
	void postConstruct() {
		productList = new ArrayList<ProductColor>();

		if (selectedCategory == null) {
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
		this.id = id;
		if (id != 0) {
			try {
				selectedProduct = productColorService.getProductById(id);
				this.availabel = "Yes";
				this.isAvailabel = true;
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

	public ProductColorSize getProductColorSize() {
		return productColorSize;
	}

	public void setProductColorSize(ProductColorSize productColorSize) {
		this.productColorSize = productColorSize;
	}

	public String getAvailabel() {
		return availabel;
	}

	public void setAvailabel(String availabel) {
		this.availabel = availabel;
	}

	public Boolean getIsAvailabel() {
		return isAvailabel;
	}

	public void setIsAvailabel(Boolean isAvailabel) {
		this.isAvailabel = isAvailabel;
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

		setId(product.getId_prod_col());
		redirect("/Shop4j/client/user/products/" + product.getId_prod_col());
	}

	public void changeSize(){
		System.out.println(productColorSize.getNrOfPieces());
	}
}
