package com.siemens.ctbav.intership.shop.service.internationalization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ejb.Stateful;

@Stateful(name = "internationalizationService")
public class InternationalizationService {
	private Locale currentLocale;
	private ResourceBundle messages;

	public void setCurrentLocale(String language, String country, String path) {
		currentLocale = new Locale(language, country);
		messages = ResourceBundle.getBundle(path, currentLocale);
	}

	public String getMessage(String message) {
		try {
			return messages.getString(message);
		} catch (MissingResourceException e) {
			return "";
		}

	}
}
