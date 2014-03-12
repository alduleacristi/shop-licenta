package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;
import com.siemens.ctbav.intership.shop.service.superadmin.PhotoService;

@ManagedBean(name = "productDescriptionReduceClient")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "productReduceDescription2", pattern = "/client/user/productReduceDescription/#{productDescriptionReduceClient.id}", viewId = "/client/user/productReduceDescription.xhtml") })
public class DescriptionProductReduceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7926484330132672880L;

	@ManagedProperty(value = "#{id}")
	private long id;

	@EJB
	private ProductColorService productColorService;

	@EJB
	private PhotoService photoService;
	
	@EJB
	private ConfigurationService confService;

	private List<ProductColor> productsColor;

	private List<ProductColorSize> productsColorSize;

	private Product selectedProduct;

	private Long idProductColor;

	private Long idProductColorSize;

	private ProductColorSize productColorSize;

	private boolean isAvailabel;

	private String availabel,host;

	private Integer nrOfPieces;

	private List<String> photos;
	
	@PostConstruct
	private void postConstruct(){
		this.host = confService.getHost();
	}

	public List<ProductColor> getProductsColor() {
		return productsColor;
	}

	public void setProductsColor(List<ProductColor> productsColor) {
		this.productsColor = productsColor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		if (id != 0) {
			initialize();
		}
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectdProduct) {
		this.selectedProduct = selectdProduct;
	}

	public Long getIdProductColor() {
		return idProductColor;
	}

	public void setIdProductColor(Long idProductColor) {
		this.idProductColor = idProductColor;
	}

	public List<ProductColorSize> getProductsColorSize() {
		return productsColorSize;
	}

	public void setProductsColorSize(List<ProductColorSize> productsColorSize) {
		this.productsColorSize = productsColorSize;
	}

	public Long getIdProductColorSize() {
		return idProductColorSize;
	}

	public void setIdProductColorSize(Long idProductColorSize) {
		this.idProductColorSize = idProductColorSize;
	}

	public ProductColorSize getProductColorSize() {
		return productColorSize;
	}

	public void setProductColorSize(ProductColorSize productColorSize) {
		this.productColorSize = productColorSize;
	}

	public boolean getIsAvailabel() {
		return isAvailabel;
	}

	public void setIsAvailabel(boolean isAvailabel) {
		this.isAvailabel = isAvailabel;
	}

	public String getAvailabel() {
		return availabel;
	}

	public void setAvailabel(String availabel) {
		this.availabel = availabel;
	}

	public Integer getNrOfPieces() {
		return nrOfPieces;
	}

	public void setNrOfPieces(Integer nrOfPieces) {
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

	private void initialize() {
		nrOfPieces = 1;
		productsColor = productColorService.getColorsForProduct(id);

		if (productsColor.size() > 0) {
			selectedProduct = productsColor.get(0).getProduct();
			displayPhotos(productsColor.get(0).getId());
		}
	}

	public void changeColor() {
		boolean ok = false;
		for (ProductColor pc : productsColor) {
			if (pc.getId() == idProductColor) {
				productsColorSize = pc.getProductColorSize();
				displayPhotos(idProductColor);
				ok = true;
			}
		}
		if (ok == false)
			productsColorSize = new ArrayList<ProductColorSize>();
		this.isAvailabel = false;
		this.availabel = "";
		idProductColorSize = 0L;
	}

	public void changeSize() {
		boolean ok = false;
		for (ProductColorSize pcs : productsColorSize) {
			if (pcs.getId() == idProductColorSize && pcs.getNrOfPieces() > 0) {
				productColorSize = pcs;
				this.availabel = "In stock";
				this.isAvailabel = true;
				ok = true;
				productColorSize = pcs;
			}
		}
		if (ok == false) {
			this.availabel = "Not in stock";
			this.isAvailabel = false;
		}
	}

	public String getProductColorId(Product p) {
		if (idProductColor != null)
			return idProductColor.toString();
		if (p == null)
			System.out.println("e null");
		List<ProductColor> productsColor = productColorService
				.getColorsForProduct(p.getId());
		if (productsColor.size() > 0)
			return productsColor.get(0).getId().toString();
		return "-1";
	}

	private void displayPhotos(Long idProductColor) {
		photos = new ArrayList<String>();
		try {
			photos = photoService.displayOfPhotos("id"
					+ idProductColor);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
