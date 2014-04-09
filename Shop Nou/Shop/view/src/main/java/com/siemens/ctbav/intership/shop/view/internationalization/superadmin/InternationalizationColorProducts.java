package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EColorProducts;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationColorProducts")
@RequestScoped
public class InternationalizationColorProducts implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String message;
	private String category;
	private String description;
	private String price;
	private String colors;
	private String productDetail;
	private String productName;
	private String productCategory;
	private String selectColor;
	private String productPrice;
	private String productDescription;

	@PostConstruct
	private void init() {
		internationalizationInit();
	}

	private void internationalizationInit() {
		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			String language = new String("en");
			String country = new String("US");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/colorProducts/ColorProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/colorProducts/ColorProducts");
		}
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EColorProducts.MESSAGE
				.getName());
		return message;
	}

	public String getCategory() {
		category = internationalizationService
				.getMessage(EColorProducts.CATEGORY.getName());
		return category;
	}

	public String getDescription() {
		description = internationalizationService
				.getMessage(EColorProducts.DESCRIPTION.getName());
		return description;
	}

	public String getPrice() {
		price = internationalizationService.getMessage(EColorProducts.PRICE
				.getName());
		return price;
	}

	public String getColors() {
		colors = internationalizationService.getMessage(EColorProducts.COLORS
				.getName());
		return colors;
	}

	public String getProductDetail() {
		productDetail = internationalizationService
				.getMessage(EColorProducts.PROD_DETAIL.getName());
		return productDetail;
	}

	public String getProductName() {
		productName = internationalizationService
				.getMessage(EColorProducts.PROD_NAME.getName());
		return productName;
	}

	public String getProductCategory() {
		productCategory = internationalizationService
				.getMessage(EColorProducts.PROD_CATEG.getName());
		return productCategory;
	}

	public String getSelectColor() {
		selectColor = internationalizationService
				.getMessage(EColorProducts.SELECT_COLOR.getName());
		return selectColor;
	}

	public String getProductPrice() {
		productPrice = internationalizationService
				.getMessage(EColorProducts.PROD_PRICE.getName());
		return productPrice;
	}

	public String getProductDescription() {
		productDescription = internationalizationService
				.getMessage(EColorProducts.PROD_DESCRIPTION.getName());
		return productDescription;
	}
}
