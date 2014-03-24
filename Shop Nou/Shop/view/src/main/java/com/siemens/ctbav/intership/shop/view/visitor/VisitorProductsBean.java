package com.siemens.ctbav.intership.shop.view.visitor;

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
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;
import com.siemens.ctbav.intership.shop.service.superadmin.PhotoService;

@ManagedBean(name = "visitorProducts")
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "productForClient", pattern = "/client/user/products/#{clientProducts.id}", viewId = "/client/user/productDescription.xhtml"),
		@URLMapping(id = "product7", pattern = "/client/products/#{clientProducts.id}", viewId = "/client/productDescription.xhtml") })
public class VisitorProductsBean implements Serializable {

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

	@EJB
	private PhotoService photoService;

	@EJB
	private ConfigurationService confService;

	private ProductColor selectedProduct;
	private Category selectedCategory;
	private List<ProductColor> productList;
	private String availabel, host;
	private boolean isAvailabel;
	private ProductColorSize productColorSize;
	private Long idProductColorSize;
	private List<String> photos;
	private Long nrOfPieces;

	@PostConstruct
	private void postConstruct() {
		this.host = confService.getHost();

		this.nrOfPieces = 1L;
		setIsAvailabel(true);
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
		System.out.println("in setId clientProducts: " + id);
		this.id = id;
		if (id != 0) {
			try {
				selectedProduct = productColorService.getProductById(id);
				// this.isAvailabel = false;
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap()
						.put("selectedProduct", selectedProduct);
				displayPhotos();
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
		// System.out.println("sa setat isAvailabel la: "+isAvailabel);
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

	public Long getNrOfPieces() {
		return nrOfPieces;
	}

	public void setNrOfPieces(Long nrOfPieces) {
		this.nrOfPieces = nrOfPieces;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
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
		}
	}

	public void showProduct(ProductColor product) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("myObj", product);

		setId(product.getId());
		String url = "/Shop4j/client/user/products/" + product.getId();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("urlProduct", url);

		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("client") != null) {
			redirect(url);
		} else {
			redirect("/Shop4j/Login");
		}
	}

	public void changeSize() {
		System.out.println("change size " + idProductColorSize + " "
				+ isAvailabel);
		try {
			// idProductColorSize = (Long) event.getNewValue();
			if (idProductColorSize == 0) {
				this.availabel = "";
				setIsAvailabel(true);
				return;
			}
			this.productColorSize = productColorSizeService
					.getProductColorSizeById(idProductColorSize);

			if (this.productColorSize.getNrOfPieces() == 0) {
				this.availabel = "Not in stock";
				setIsAvailabel(true);
			} else {
				this.availabel = "In stock";
				setIsAvailabel(false);
			}
		} catch (ProductColorSizeDoesNotExistException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Sorry. You can't choose products now."));
			this.availabel = "";
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private void displayPhotos() {
		photos = new ArrayList<String>();
		try {
			photos = photoService.displayOfPhotos("id"
					+ selectedProduct.getId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
