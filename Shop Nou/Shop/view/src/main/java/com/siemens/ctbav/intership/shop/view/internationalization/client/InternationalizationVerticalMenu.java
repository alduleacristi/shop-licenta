package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.EVerticalMenu;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationVerticalMenu")
@RequestScoped
public class InternationalizationVerticalMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String options;
	private String personalData;
	private String securityData;
	private String viewHistory;

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
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/client/menu/verticalMenu/VerticalMenu");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/client/menu/verticalMenu/VerticalMenu");
		}
	}

	public String getOptions() {
		options = internationalizationService.getMessage(EVerticalMenu.OPTIONS
				.getName());
		return options;
	}

	public String getPersonalData() {
		personalData = internationalizationService
				.getMessage(EVerticalMenu.PERSONAL_DATA.getName());
		return personalData;
	}

	public String getSecurityData() {
		securityData = internationalizationService
				.getMessage(EVerticalMenu.SECURITY_DATA.getName());
		return securityData;
	}

	public String getViewHistory() {
		viewHistory = internationalizationService
				.getMessage(EVerticalMenu.VIEW_HISTORY.getName());
		return viewHistory;
	}
}
