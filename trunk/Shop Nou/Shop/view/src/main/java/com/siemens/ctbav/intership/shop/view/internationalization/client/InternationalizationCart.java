package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.ECart;

@ManagedBean(name = "internationalizationCart")
@RequestScoped
public class InternationalizationCart implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String cart;
	private String articles;
	private String cost;
	private String remove;
	private String products;
	private String noRecords;
	private String sendCommand;
	private String existAddress;
	private String newAddress;
	private String anotherAddress;

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
					"internationalization/client/cart/Cart");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/cart/Cart");
		}
	}

	public String getCart() {
		cart = internationalizationService.getMessage(ECart.CART.getName());
		return cart;
	}

	public String getArticles() {
		articles = internationalizationService.getMessage(ECart.ARTICLES
				.getName());
		return articles;
	}

	public String getCost() {
		cost = internationalizationService.getMessage(ECart.COST.getName());
		return cost;
	}

	public String getRemove() {
		remove = internationalizationService.getMessage(ECart.REMOVE.getName());
		return remove;
	}

	public String getProducts() {
		products = internationalizationService.getMessage(ECart.PRODUCTS
				.getName());
		return products;
	}

	public String getNoRecords() {
		noRecords = internationalizationService.getMessage(ECart.NO_RECORDS
				.getName());
		return noRecords;
	}

	public String getSendCommand() {
		sendCommand = internationalizationService.getMessage(ECart.SEND_COMMAND
				.getName());
		return sendCommand;
	}

	public String getExistAddress() {
		existAddress = internationalizationService
				.getMessage(ECart.EXIST_ADDRESS.getName());
		return existAddress;
	}

	public String getNewAddress() {
		newAddress = internationalizationService.getMessage(ECart.NEW_ADDRESS
				.getName());
		return newAddress;
	}

	public String getAnotherAddress() {
		anotherAddress = internationalizationService
				.getMessage(ECart.ANOTHER_ADDRESS.getName());
		return anotherAddress;
	}
}
