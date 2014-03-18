package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EUnimplemented;

@ManagedBean(name = "internationalizationUnimplemented")
@RequestScoped
public class InternationalizationUnimplemented implements Serializable {
	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String sorry;
	private String message;

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
					"internationalization/unimplemented/Unimplemented");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/unimplemented/Unimplemented");
		}
	}

	public String getSorry() {
		sorry = internationalizationService.getMessage(EUnimplemented.SORRY
				.getName());
		return sorry;
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EUnimplemented.MESSAGE
				.getName());
		return message;
	}
}
