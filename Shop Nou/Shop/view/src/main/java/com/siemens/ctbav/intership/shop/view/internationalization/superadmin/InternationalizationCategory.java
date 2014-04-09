package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.ECategory;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationCategory")
@RequestScoped
public class InternationalizationCategory implements Serializable {

	private static final long serialVersionUID = -1539289596311208991L;

	@EJB
	private InternationalizationService internationalizationService;

	private String template1;
	private String template2;
	private String createM;
	private String updateM;
	private String selectParent;

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
							"internationalization/superadmin/messages/categories/Categories");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/categories/Categories");
		}
	}

	public String getTemplate1() {
		template1 = internationalizationService.getMessage(ECategory.TEMPLATE1
				.getName());
		return template1;
	}

	public String getTemplate2() {
		template2 = internationalizationService.getMessage(ECategory.TEMPLATE2
				.getName());
		return template2;
	}

	public String getCreateM() {
		createM = internationalizationService.getMessage(ECategory.CREATEM
				.getName());
		return createM;
	}

	public String getUpdateM() {
		updateM = internationalizationService.getMessage(ECategory.UPDATEM
				.getName());
		return updateM;
	}

	public String getSelectParent() {
		selectParent = internationalizationService
				.getMessage(ECategory.SELECTPARENT.getName());
		return selectParent;
	}
}
