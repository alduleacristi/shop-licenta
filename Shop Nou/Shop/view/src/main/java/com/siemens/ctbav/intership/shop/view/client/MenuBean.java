package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.EClientMessages;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@RequestScoped
@ManagedBean(name = "menuBean")
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -8309676019275165122L;

	@EJB
	private InternationalizationService internationalizationService;

	@PostConstruct
	private void initialize() {
		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		internationalizationInit();
		this.name = internationalizationService.getMessage(EClientMessages.HI
				.getName())
				+ " "
				+ client.getFirstname()
				+ " "
				+ client.getLastname();
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
					"internationalization/client/messages/ClientMessages");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/messages/ClientMessages");
		}
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
