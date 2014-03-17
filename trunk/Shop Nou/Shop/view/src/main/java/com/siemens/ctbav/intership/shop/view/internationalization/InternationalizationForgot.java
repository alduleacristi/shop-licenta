package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EForgot;

@ManagedBean(name = "internationalizationForgot")
@RequestScoped
public class InternationalizationForgot implements Serializable {

	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String forgot;
	private String enterEmail;
	private String email;
	private String changePassword;
	private String password;
	private String retype;

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
					"internationalization/forgotPassword/ForgotPassword");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/forgotPassword/ForgotPassword");
		}
	}

	public String getForgot() {
		forgot = internationalizationService.getMessage(EForgot.FORGOT
				.getName());
		return forgot;
	}

	public String getEnterEmail() {
		enterEmail = internationalizationService.getMessage(EForgot.ENTER_EMAIL
				.getName());
		return enterEmail;
	}

	public String getEmail() {
		email = internationalizationService.getMessage(EForgot.EMAIL.getName());
		return email;
	}

	public String getChangePassword() {
		changePassword = internationalizationService
				.getMessage(EForgot.CHANGE_PASSWORD.getName());
		return changePassword;
	}

	public String getPassword() {
		password = internationalizationService.getMessage(EForgot.PASSWORD
				.getName());
		return password;
	}

	public String getRetype() {
		retype = internationalizationService.getMessage(EForgot.RETYPE
				.getName());
		return retype;
	}
}
