package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.ECoin;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationCoinSign")
@ViewScoped
public class InternationalizationCoinSign implements Serializable {

	private static final long serialVersionUID = 5599100413817569586L;

	@EJB
	private InternationalizationService internationalizationService;

	private String coin;
	private String update;
	private String add;

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
					"internationalization/coinSign/Coin");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/coinSign/Coin");
		}
	}

	public String getCoin() {
		coin = internationalizationService.getMessage(ECoin.COIN.getName());
		return coin;
	}

	public String getUpdate() {
		update = internationalizationService.getMessage(ECoin.UPDATE.getName());
		return update;
	}

	public String getAdd() {
		add = internationalizationService.getMessage(ECoin.ADD.getName());
		return add;
	}

}
