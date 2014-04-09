package com.siemens.ctbav.intership.shop.view.internationalization;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.ELogin;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationLoginBean")
@RequestScoped
public class InternationalizationLoginBean {

	@EJB
	private InternationalizationService internationalizationService;

	private boolean isEnglishSelected;
	private String login;
	private String username;
	private String password;
	private String forgot;

	@PostConstruct
	private void init() {
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
					"internationalization/login/Login");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/login/Login");
		}
	}

	public String getLogin() {
		login = internationalizationService.getMessage(ELogin.LOGIN.getName());
		return login;
	}

	public String getUsername() {
		username = internationalizationService.getMessage(ELogin.USERNAME
				.getName());
		return username;
	}

	public String getPassword() {
		password = internationalizationService.getMessage(ELogin.PASSWORD
				.getName());
		return password;
	}

	public String getForgot() {
		forgot = internationalizationService
				.getMessage(ELogin.FORGOT.getName());
		return forgot;
	}
}
