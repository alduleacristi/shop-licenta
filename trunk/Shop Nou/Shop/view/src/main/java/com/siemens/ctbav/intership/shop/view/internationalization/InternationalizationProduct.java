package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EProduct;

@ManagedBean(name = "internationalizationProduct")
@RequestScoped
public class InternationalizationProduct implements Serializable {
	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String productName;
	private String price;
	private String outOfStock;
	private String inStock;
	private String productInfo;
	private String name;
	private String category;
	private String description;
	private String color;
	private String chooseColor;
	private String chooseSize;
	private String nrPieces;
	private String add;
	private String changeQty;
	private String itemsNow;
	private String size;
	private String recommand;

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
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/product/Product");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/product/Product");
		}
	}

	public String getProductName() {
		productName = internationalizationService
				.getMessage(EProduct.PRODUCT_NAME.getName());
		return productName;
	}

	public String getPrice() {
		price = internationalizationService
				.getMessage(EProduct.PRICE.getName());
		return price;
	}

	public String getOutOfStock() {
		outOfStock = internationalizationService
				.getMessage(EProduct.OUT_OF_STOCK.getName());
		return outOfStock;
	}

	public String getInStock() {
		inStock = internationalizationService.getMessage(EProduct.IN_STOCK
				.getName());
		return inStock;
	}

	public String getProductInfo() {
		productInfo = internationalizationService.getMessage(EProduct.PROD_INFO
				.getName());
		return productInfo;
	}

	public String getName() {
		name = internationalizationService.getMessage(EProduct.NAME.getName());
		return name;
	}

	public String getCategory() {
		category = internationalizationService.getMessage(EProduct.CATEGORY
				.getName());
		return category;
	}

	public String getDescription() {
		description = internationalizationService
				.getMessage(EProduct.DESCRIPTION.getName());
		return description;
	}

	public String getColor() {
		color = internationalizationService
				.getMessage(EProduct.COLOR.getName());
		return color;
	}

	public String getChooseColor() {
		chooseColor = internationalizationService
				.getMessage(EProduct.CHOOSE_COLOR.getName());
		return chooseColor;
	}

	public String getChooseSize() {
		chooseSize = internationalizationService
				.getMessage(EProduct.CHOOSE_SIZE.getName());
		return chooseSize;
	}

	public String getNrPieces() {
		nrPieces = internationalizationService.getMessage(EProduct.NR_PIECES
				.getName());
		return nrPieces;
	}

	public String getAdd() {
		add = internationalizationService.getMessage(EProduct.ADD.getName());
		return add;
	}

	public String getChangeQty() {
		changeQty = internationalizationService.getMessage(EProduct.CHANGE_QTY
				.getName());
		return changeQty;
	}

	public String getItemsNow() {
		itemsNow = internationalizationService.getMessage(EProduct.ITEMS_NOW
				.getName());
		return itemsNow;
	}

	public String getSize() {
		size = internationalizationService.getMessage(EProduct.SIZE.getName());
		return size;
	}

	public String getRecommand() {
		recommand = internationalizationService.getMessage(EProduct.RECOMMAND
				.getName());
		return recommand;
	}
}
