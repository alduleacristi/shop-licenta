package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.ECreateAccount;

@ManagedBean(name = "internationalizationCreateAccount")
@RequestScoped
public class InternationalizationCreateAccount implements Serializable {

	private static final long serialVersionUID = 2013636671192124342L;

	@EJB
	private InternationalizationService internationalizationService;

	private String header;
	private String succesMessage;

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
					"internationalization/client/createAccount/CreateAccount");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/createAccount/CreateAccount");
		}
	}

	public String getHeader() {
		header = internationalizationService.getMessage(ECreateAccount.HEADER
				.getName());
		return header;
	}

	public String getSuccesMessage() {
		succesMessage = internationalizationService.getMessage(ECreateAccount.SUCCESS_MESSAGE.getName());
		return succesMessage;
	}

}
