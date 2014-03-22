package com.siemens.ctbav.intership.shop.view.client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.util.client.NavigationUtil;
import com.siemens.ctbav.intership.shop.view.visitor.VisitorProductsBean;

@ManagedBean(name = "clientProducts")
@ViewScoped
public class ClientProductsBean extends VisitorProductsBean {

	private static final long serialVersionUID = 7758675476994236839L;

	@Override
	public void showProduct(ProductColor product) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("myObj", product);

		setId(product.getId());
		NavigationUtil.redirect("/Shop4j/client/user/products/" + product.getId());
	}
}
