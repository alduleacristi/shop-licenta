package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EGeneric;

@ManagedBean(name = "internationalizationGeneric")
@RequestScoped
public class InternationalizationGeneric implements Serializable {

	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String create;
	private String delete;
	private String update;
	private String yes;
	private String no;
	private String confirmMessage;
	private String confirmation;
	private String name;
	private String cancel;

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
					"internationalization/superadmin/generic/Generic");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/generic/Generic");
		}
	}

	public String getCreate() {
		create = internationalizationService.getMessage(EGeneric.CREATE
				.getName());
		return create;
	}

	public String getDelete() {
		delete = internationalizationService.getMessage(EGeneric.DELETE
				.getName());
		return delete;
	}

	public String getUpdate() {
		update = internationalizationService.getMessage(EGeneric.UPDATE
				.getName());
		return update;
	}

	public String getYes() {
		yes = internationalizationService.getMessage(EGeneric.YES.getName());
		return yes;
	}

	public String getNo() {
		no = internationalizationService.getMessage(EGeneric.NO.getName());
		return no;
	}

	public String getConfirmMessage() {
		confirmMessage = internationalizationService
				.getMessage(EGeneric.CONFIRM_MESSAGE.getName());
		return confirmMessage;
	}

	public String getName() {
		name = internationalizationService.getMessage(EGeneric.NAME.getName());
		return name;
	}

	public String getConfirmation() {
		confirmation = internationalizationService
				.getMessage(EGeneric.CONFIRMATION.getName());
		return confirmation;
	}

	public String getCancel() {
		cancel = internationalizationService.getMessage(EGeneric.CANCEL
				.getName());
		return cancel;
	}
}
