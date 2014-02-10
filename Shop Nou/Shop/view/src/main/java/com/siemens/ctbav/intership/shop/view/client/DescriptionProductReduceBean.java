package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.ProductColor;
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
	
	@PostConstruct
	private void initialize() {
		System.out.println("iau culorile");
		productsColor = productColorService.getColorsForProduct(id);
		
		for(ProductColor pc : productsColor)
			System.out.println(pc.getColor());
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
	}

	
}
