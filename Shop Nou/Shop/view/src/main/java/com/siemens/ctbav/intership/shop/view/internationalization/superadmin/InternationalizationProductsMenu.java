package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EMenuProducts;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationProductMenuBean")
@RequestScoped
public class InternationalizationProductsMenu {

	@EJB
	private InternationalizationService internationalizationService;

	private boolean isEnglishSelected;
	private String manageGeneric;
	private String manageColors;
	private String managePhotos;
	private String manageSizes;

	@PostConstruct
	private void init() {
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
							"internationalization/superadmin/menu/menuProducts/MenuProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/menu/menuProducts/MenuProducts");
		}
	}

	public String getManageGeneric() {
		manageGeneric = internationalizationService
				.getMessage(EMenuProducts.MANAGE_GENERIC.getName());
		return manageGeneric;
	}

	public String getManageColors() {
		manageColors = internationalizationService
				.getMessage(EMenuProducts.MANAGE_COLORS.getName());
		return manageColors;
	}

	public String getManagePhotos() {
		managePhotos = internationalizationService
				.getMessage(EMenuProducts.MANAGE_PHOTOS.getName());
		return managePhotos;
	}

	public String getManageSizes() {
		manageSizes = internationalizationService
				.getMessage(EMenuProducts.MANAGE_SIZES.getName());
		return manageSizes;
	}
}
