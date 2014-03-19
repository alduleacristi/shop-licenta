package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EGenericProduct;

@ManagedBean(name = "internationalizationGenericProducts")
@RequestScoped
public class InternationalizationGenericProducts implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String message;
	private String category;
	private String description;
	private String price;
	private String sale;
	private String productDetail;
	private String productName;
	private String productCategory;
	private String changeParent;
	private String productPrice;
	private String productDescription;
	private String nameIsRequired;
	private String priceIsRequired;
	private String saleIsRequired;
	private String reindexButton;
	private String reindexTooltip;

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
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		}
	}

	public String getMessage() {
		message = internationalizationService
				.getMessage(EGenericProduct.MESSAGE.getName());
		return message;
	}

	public String getCategory() {
		category = internationalizationService
				.getMessage(EGenericProduct.CATEGORY.getName());
		return category;
	}

	public String getDescription() {
		description = internationalizationService
				.getMessage(EGenericProduct.DESCRIPTION.getName());
		return description;
	}

	public String getPrice() {
		price = internationalizationService.getMessage(EGenericProduct.PRICE
				.getName());
		return price;
	}

	public String getSale() {
		sale = internationalizationService.getMessage(EGenericProduct.SALE
				.getName());
		return sale;
	}

	public String getProductDetail() {
		productDetail = internationalizationService
				.getMessage(EGenericProduct.PROD_DETAIL.getName());
		return productDetail;
	}

	public String getProductName() {
		productName = internationalizationService
				.getMessage(EGenericProduct.PROD_NAME.getName());
		return productName;
	}

	public String getProductCategory() {
		productCategory = internationalizationService
				.getMessage(EGenericProduct.PROD_CATEG.getName());
		return productCategory;
	}

	public String getChangeParent() {
		changeParent = internationalizationService
				.getMessage(EGenericProduct.CHANGE_PARENT.getName());
		return changeParent;
	}

	public String getProductPrice() {
		productPrice = internationalizationService
				.getMessage(EGenericProduct.PROD_PRICE.getName());
		return productPrice;
	}

	public String getProductDescription() {
		productDescription = internationalizationService
				.getMessage(EGenericProduct.PROD_DESCRIPTION.getName());
		return productDescription;
	}

	public String getNameIsRequired() {
		nameIsRequired = internationalizationService
				.getMessage(EGenericProduct.NAME_IS_REQUIRED.getName());
		return nameIsRequired;
	}

	public String getPriceIsRequired() {
		priceIsRequired = internationalizationService
				.getMessage(EGenericProduct.PRICE_IS_REQUIRED.getName());
		return priceIsRequired;
	}

	public String getSaleIsRequired() {
		saleIsRequired = internationalizationService
				.getMessage(EGenericProduct.SALE_REQUIRED.getName());
		return saleIsRequired;
	}

	public String getReindexButton() {
		reindexButton = internationalizationService
				.getMessage(EGenericProduct.REINDEX_BUTTON.getName());
		return reindexButton;
	}

	public String getReindexTooltip() {
		reindexTooltip = internationalizationService
				.getMessage(EGenericProduct.REINDEX_TOOLTIP.getName());
		return reindexTooltip;
	}
}
