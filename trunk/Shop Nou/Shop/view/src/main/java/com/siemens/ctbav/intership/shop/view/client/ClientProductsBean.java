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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;

@ManagedBean(name = "clientProducts")
@ViewScoped
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

	@EJB
	ProductColorSizeService productColorSizeService;

	private ProductColor selectedProduct;
	private Category selectedCategory;
	private List<ProductColor> productList;
	private String availabel;
	private Boolean isAvailabel;
	private ProductColorSize productColorSize;
	private Long idProductColorSize;

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
				this.isAvailabel = false;
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

	public Long getIdProductColorSize() {
		return idProductColorSize;
	}

	public void setIdProductColorSize(Long idProductColorSize) {
		this.idProductColorSize = idProductColorSize;
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
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("myObj", product);

		setId(product.getId());
		redirect("/Shop4j/client/user/products/" + product.getId());
	}

	public void changeSize() {
		try {
			this.productColorSize = productColorSizeService
					.getProductColorSizeById(idProductColorSize);
			
			if (this.productColorSize.getNrOfPieces() == 0) {
				this.availabel = "Not in stock";
				this.isAvailabel = false;
			} else {
				this.availabel = "In stock";
				this.isAvailabel = true;
			}
		} catch (ProductColorSizeDoesNotExistException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Sorry. You can't choose products now."));
			this.availabel = "";
		}
	}
}
