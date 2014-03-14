package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.ESearchBox;

@ManagedBean(name = "internationalizationSearchBox")
@RequestScoped
public class InternationalizationSearchBox implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	public String searchBy;
	public String keyword;
	public String price;
	public String search;
	public String support;
	public String categories;

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
					"internationalization/client/menu/searchBox/SearchBox");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/menu/searchBox/SearchBox");
		}
	}

	public String getSearchBy() {
		searchBy = internationalizationService.getMessage(ESearchBox.SEARCH_BY
				.getName());
		return searchBy;
	}

	public String getKeyword() {
		keyword = internationalizationService.getMessage(ESearchBox.KEYWORD
				.getName());
		return keyword;
	}

	public String getPrice() {
		price = internationalizationService.getMessage(ESearchBox.PRICE
				.getName());
		return price;
	}

	public String getSearch() {
		search = internationalizationService.getMessage(ESearchBox.SEARCH
				.getName());
		return search;
	}

	public String getSupport() {
		support = internationalizationService.getMessage(ESearchBox.SUPPORT
				.getName());
		return support;
	}

	public String getCategories() {
		categories = internationalizationService
				.getMessage(ESearchBox.CATEGORIES.getName());
		return categories;
	}

}
