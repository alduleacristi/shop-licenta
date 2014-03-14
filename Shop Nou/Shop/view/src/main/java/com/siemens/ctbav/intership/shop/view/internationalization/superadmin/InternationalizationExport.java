package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EExport;

@ManagedBean(name = "internationalizationExport")
@RequestScoped
public class InternationalizationExport implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String xml;
	private String csv;
	private String message;
	private String export;
	private String filename;
	private String required;

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
					"internationalization/superadmin/messages/export/Export");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/export/Export");
		}
	}

	public String getXml() {
		xml = internationalizationService.getMessage(EExport.XML.getName());
		return xml;
	}

	public String getCsv() {
		csv = internationalizationService.getMessage(EExport.CSV.getName());
		return csv;
	}

	public String getMessage() {
		message = internationalizationService.getMessage(EExport.MESSAGE
				.getName());
		return message;
	}

	public String getExport() {
		export = internationalizationService.getMessage(EExport.EXPORT
				.getName());
		return export;
	}

	public String getFilename() {
		filename = internationalizationService.getMessage(EExport.FILE
				.getName());
		return filename;
	}

	public String getRequired() {
		required = internationalizationService.getMessage(EExport.REQUIRED
				.getName());
		return required;
	}
}
