package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.EVerticalMenuContact;

@ManagedBean(name = "internationalizationVerticalMenuContact")
@RequestScoped
public class InternationalizationVerticalMenuContact implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String options;
	private String sendMessage;
	private String messageHistory;

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
							"internationalization/client/menu/verticalMenuContact/VerticalMenuContact");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/client/menu/verticalMenuContact/VerticalMenuContact");
		}
	}

	public String getOptions() {
		options = internationalizationService
				.getMessage(EVerticalMenuContact.OPTIONS.getName());
		return options;
	}

	public String getSendMessage() {
		sendMessage = internationalizationService
				.getMessage(EVerticalMenuContact.SEND_MESSAGE.getName());
		return sendMessage;
	}

	public String getMessageHistory() {
		messageHistory = internationalizationService
				.getMessage(EVerticalMenuContact.MESSAGE_HISTORY.getName());
		return messageHistory;
	}
}
