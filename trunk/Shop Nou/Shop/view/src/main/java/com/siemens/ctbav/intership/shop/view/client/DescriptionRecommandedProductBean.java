package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "productDescriptionRecommandedClient")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "productRecommandedDescription", pattern = "/client/user/productRecommandedDescription/#{productDescriptionRecommandedClient.id}", viewId = "/client/user/productRecommandationDescription.xhtml") })
public class DescriptionRecommandedProductBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -458323186216495981L;
	@ManagedProperty(value = "#{id}")
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
