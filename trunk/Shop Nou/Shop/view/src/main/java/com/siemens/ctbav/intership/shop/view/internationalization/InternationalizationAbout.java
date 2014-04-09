package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.EAbout;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationAbout")
@RequestScoped
public class InternationalizationAbout implements Serializable {

	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String header;
	private String footer;

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
					"internationalization/about/About");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/about/About");
		}
	}

	public String getHeader() {
		header = internationalizationService
				.getMessage(EAbout.HEADER.getName());
		return header;
	}

	public String getFooter() {
		footer = internationalizationService
				.getMessage(EAbout.FOOTER.getName());
		return footer;
	}

}
