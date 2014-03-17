package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.ESecurityData;

@ManagedBean(name = "internationalizationSecurityData")
@RequestScoped
public class InternationalizationSecurityData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String oldPass;
	private String pass;
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
					"internationalization/client/securityData/SecurityData");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/securityData/SecurityData");
		}
	}

	public String getOldPass() {
		oldPass = internationalizationService
				.getMessage(ESecurityData.OLD_PASSWORD.getName());
		return oldPass;
	}

	public String getPass() {
		pass = internationalizationService.getMessage(ESecurityData.PASSWORD
				.getName());
		return pass;
	}

	public String getRetype() {
		retype = internationalizationService.getMessage(ESecurityData.RETYPE
				.getName());
		return retype;
	}
}
