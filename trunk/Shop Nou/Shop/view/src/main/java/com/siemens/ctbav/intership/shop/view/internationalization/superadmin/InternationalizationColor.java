package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EColor;

@ManagedBean(name = "internationalizationColor")
@RequestScoped
public class InternationalizationColor implements Serializable {

	@EJB
	private InternationalizationService internationalizationService;

	private static final long serialVersionUID = -2054858067510210793L;

	private String colors;
	private String colorDetail;
	private String colorName;
	private String colorDescription;
	private String editTheColor;
	private String chose;

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
					"internationalization/superadmin/messages/colors/Colors");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/colors/Colors");
		}
	}

	public String getColors() {
		colors = internationalizationService
				.getMessage(EColor.COLORS.getName());
		return colors;
	}

	public String getColorDetail() {
		colorDetail = internationalizationService
				.getMessage(EColor.COLOR_DETAIL.getName());
		return colorDetail;
	}

	public String getColorName() {
		colorName = internationalizationService.getMessage(EColor.COLOR_NAME
				.getName());
		return colorName;
	}

	public String getColorDescription() {
		colorDescription = internationalizationService
				.getMessage(EColor.COLOR_DESCRIPTION.getName());
		return colorDescription;
	}

	public String getEditTheColor() {
		editTheColor = internationalizationService.getMessage(EColor.EDIT_COLOR
				.getName());
		return editTheColor;
	}

	public String getChose() {
		chose = internationalizationService.getMessage(EColor.CHOSE.getName());
		return chose;
	}
}
