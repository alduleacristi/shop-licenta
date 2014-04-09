package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EImport;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationImport")
@RequestScoped
public class InternationalizationImport implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String xml;
	private String csv;
	private String message;
	private String importt;

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
					"internationalization/superadmin/messages/import/Import");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/import/Import");
		}
	}

	public String getXml() {
		xml = internationalizationService.getMessage(EImport.XML.getName());
		return xml;
	}

	public String getCsv() {
		csv = internationalizationService.getMessage(EImport.CSV.getName());
		return csv;
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EImport.MESSAGE
				.getName());
		return message;
	}

	public String getImport() {
		importt = internationalizationService.getMessage(EImport.IMPORT
				.getName());
		return importt;
	}

}
