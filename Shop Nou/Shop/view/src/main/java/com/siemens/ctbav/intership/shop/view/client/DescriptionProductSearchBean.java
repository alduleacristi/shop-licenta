package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.view.visitor.DescriptionProductSearchBeanVisitor;

@ManagedBean(name = "productDescriptionSearchClient")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "productSearchDescription", pattern = "/client/user/productSearchDescription/#{productDescriptionSearchClient.id}", viewId = "/client/user/productSearchDescription.xhtml") })
public class DescriptionProductSearchBean extends
		DescriptionProductSearchBeanVisitor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1545L;

}
