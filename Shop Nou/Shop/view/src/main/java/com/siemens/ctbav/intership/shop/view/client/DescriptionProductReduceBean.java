package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorService;

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

	private List<ProductColor> productsColor;

	private List<ProductColorSize> productsColorSize;

	private Product selectedProduct;

	private Long idProductColor;
	
	private Long idProductColorSize;
	
	private ProductColorSize productColorSize;
	
	private boolean isAvailabel;
	
	private String availabel;

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
		if (id != 0)
			initialize();
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

	private void initialize() {
		//System.out.println("postconstruct description");
		//System.out.println("iau culorile cu id=" + id);
		productsColor = productColorService.getColorsForProduct(id);

		if (productsColor.size() > 0) {
			selectedProduct = productsColor.get(0).getProduct();
			System.out.println("name" + selectedProduct.getName());
			System.out.println("price" + selectedProduct.getPrice());
		}
	}

	public void changeColor() {
		boolean ok = false;
		for (ProductColor pc : productsColor) {
			if (pc.getId() == idProductColor) {
				productsColorSize = pc.getProductColorSize();
				ok = true;
			}
		}
		if(ok == false)
			productsColorSize = new ArrayList<ProductColorSize>();
		this.isAvailabel = false;
		this.availabel = "";
		idProductColorSize = 0L;
	}
	
	public void changeSize(){
		boolean ok = false;
		for(ProductColorSize pcs:productsColorSize){
			if(pcs.getId() == idProductColorSize){
				productColorSize = pcs;
				this.availabel = "In stock";
				this.isAvailabel = true;
				ok = true;
			}
		}
		if(ok == false){
			this.availabel = "Not in stock";
			this.isAvailabel = false;
		}
	}
}
