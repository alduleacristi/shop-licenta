package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.util.client.NavigationUtil;
import com.siemens.ctbav.intership.shop.view.visitor.SearchBeanVisitor;

@SessionScoped
@ManagedBean(name = "searchClient")
@URLMapping(id = "searchClient", pattern = "/client/user/Search", viewId = "/client/user/search.xhtml")
public class SearchBean extends SearchBeanVisitor implements Serializable {

	private static final long serialVersionUID = 5001018970672831617L;

	@Override
	public void search(String query) {
		if (query.length() < 3)
			return;

		String lowerQuery = query.toLowerCase();

		products = searchService.search(lowerQuery);

		for (int i = 0; i < products.size(); i++)
			if (products.get(i).getPrice() < minPrice
					|| products.get(i).getPrice() > maxPrice) {
				products.remove(i);
				i--;
			}

		System.out.println(products.size());
		if (products.size() == 0) {
			setNoProducts(false);
			System.out.println("lista e goala");
		}

		else {
			setNoProducts(true);
			System.out.println("lista nu e goala");
		}

		NavigationUtil.redirect("/Shop4j/client/user/Search");
	}

	@Override
	public void chooseAProduct(Product product) {
		NavigationUtil.redirect("/Shop4j/client/user/productSearchDescription/"
				+ product.getId());
	}
}
