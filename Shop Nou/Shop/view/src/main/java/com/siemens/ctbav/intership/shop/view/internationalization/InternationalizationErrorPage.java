package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.EErrorPage;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationErrorPage")
@ViewScoped
public class InternationalizationErrorPage implements Serializable {

	private static final long serialVersionUID = -2048078948394259106L;

	@EJB
	private InternationalizationService internationalizationService;

	private String pageNotFound;
	private String accessDenied;

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
					"internationalization/error/pageNotExist/ErrorPage");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/error/pageNotExist/ErrorPage");
		}
	}

	public String getPageNotFound() {
		pageNotFound = internationalizationService
				.getMessage(EErrorPage.ERROR_MESSAGE_PAGE_NOT_FOUND.getName());
		return pageNotFound;
	}

	public String getAccessDenied() {
		accessDenied = internationalizationService
				.getMessage(EErrorPage.ACCESS_DENIED.getName());
		return accessDenied;
	}

}
