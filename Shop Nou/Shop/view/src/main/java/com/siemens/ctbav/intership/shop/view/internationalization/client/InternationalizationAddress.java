package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.EAdress;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationAddress")
@RequestScoped
public class InternationalizationAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String country;
	private String county;
	private String locality;
	private String street;
	private String number;
	private String block;
	private String staircase;
	private String flat;
	private String message;
	private String chooseCountry;
	private String chooseCounty;
	private String chooseLocality;
	private String numberRequired;

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
					"internationalization/client/address/Address");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/address/Address");
		}
	}

	public String getCountry() {
		country = internationalizationService.getMessage(EAdress.COUNTRY
				.getName());
		return country;
	}

	public String getCounty() {
		county = internationalizationService.getMessage(EAdress.COUNTY
				.getName());
		return county;
	}

	public String getLocality() {
		locality = internationalizationService.getMessage(EAdress.LOCALITY
				.getName());
		return locality;
	}

	public String getStreet() {
		street = internationalizationService.getMessage(EAdress.STREET
				.getName());
		return street;
	}

	public String getNumber() {
		number = internationalizationService.getMessage(EAdress.NUMBER
				.getName());
		return number;
	}

	public String getBlock() {
		block = internationalizationService.getMessage(EAdress.BLOCK.getName());
		return block;
	}

	public String getStaircase() {
		staircase = internationalizationService.getMessage(EAdress.STAIRCASE
				.getName());
		return staircase;
	}

	public String getFlat() {
		flat = internationalizationService.getMessage(EAdress.FLAT.getName());
		return flat;
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EAdress.MESSAGES
				.getName());
		return message;
	}

	public String getChooseCountry() {
		chooseCountry = internationalizationService
				.getMessage(EAdress.CHOOSE_COUNTRY.getName());
		return chooseCountry;
	}

	public String getChooseCounty() {
		chooseCounty = internationalizationService
				.getMessage(EAdress.CHOOSE_COUNTY.getName());
		return chooseCounty;
	}

	public String getChooseLocality() {
		chooseLocality = internationalizationService
				.getMessage(EAdress.CHOOSE_LOCALITY.getName());
		return chooseLocality;
	}

	public String getNumberRequired() {
		numberRequired = internationalizationService
				.getMessage(EAdress.NUMBER_REQUIRED.getName());
		return numberRequired;
	}
}
