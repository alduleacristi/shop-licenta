package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.EMenuBox;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationMenuBox")
@RequestScoped
public class InternationalizationMenuBox implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String home;
	private String create;
	private String contact;
	private String store;
	private String login;
	private String logout;
	private String account;

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
					"internationalization/client/menu/menuBox/MenuBox");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/menu/menuBox/MenuBox");
		}
	}

	public String getHome() {
		home = internationalizationService.getMessage(EMenuBox.HOME.getName());
		return home;
	}

	public String getCreate() {
		create = internationalizationService.getMessage(EMenuBox.CREATE
				.getName());
		return create;
	}

	public String getContact() {
		contact = internationalizationService.getMessage(EMenuBox.CONTACT
				.getName());
		return contact;
	}

	public String getStore() {
		store = internationalizationService
				.getMessage(EMenuBox.STORE.getName());
		return store;
	}

	public String getLogin() {
		login = internationalizationService
				.getMessage(EMenuBox.LOGIN.getName());
		return login;
	}

	public String getLogout() {
		logout = internationalizationService.getMessage(EMenuBox.LOGOUT
				.getName());
		return logout;
	}

	public String getAccount() {
		account = internationalizationService.getMessage(EMenuBox.ACCOUNT
				.getName());
		return account;
	}

}
