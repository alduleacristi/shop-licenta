package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EContact;

@ManagedBean(name = "internationalizationContact")
@RequestScoped
public class InternationalizationContact implements Serializable {
	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String send;
	private String message;
	private String subject;
	private String info;
	private String requiredSubject;
	private String requiredMessage;

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
					"internationalization/contact/Contact");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/contact/Contact");
		}
	}

	public String getSend() {
		send = internationalizationService.getMessage(EContact.SEND.getName());
		return send;
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EContact.MESSAGE
				.getName());
		return message;
	}

	public String getSubject() {
		subject = internationalizationService.getMessage(EContact.SUBJECT
				.getName());
		return subject;
	}

	public String getInfo() {
		info = internationalizationService.getMessage(EContact.INFO.getName());
		return info;
	}

	public String getRequiredSubject() {
		requiredSubject = internationalizationService
				.getMessage(EContact.REQUIRED_SUBJECT.getName());
		return requiredSubject;
	}

	public String getRequiredMessage() {
		requiredMessage = internationalizationService
				.getMessage(EContact.REQUIRED_MESSAGE.getName());
		return requiredMessage;
	}
}
