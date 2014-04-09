package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.EPersonalData;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationPersonalData")
@RequestScoped
public class InternationalizationPersonalData implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String firstName;
	private String lastName;
	private String phone;

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
					"internationalization/client/personalData/PersonalData");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/personalData/PersonalData");
		}
	}

	public String getFirstName() {
		firstName = internationalizationService
				.getMessage(EPersonalData.FIRST_NAME.getName());
		return firstName;
	}

	public String getLastName() {
		lastName = internationalizationService
				.getMessage(EPersonalData.LAST_NAME.getName());
		return lastName;
	}

	public String getPhone() {
		phone = internationalizationService.getMessage(EPersonalData.PHONE
				.getName());
		return phone;
	}

}
