package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EAdvertise;

@ManagedBean(name = "internationalizationAdvertise")
@RequestScoped
public class InternationalizationAdvertise implements Serializable {

	private static final long serialVersionUID = -6500826395561045431L;

	@EJB
	private InternationalizationService internationalizationService;

	private String advertise1;
	private String site1;
	private String title1;
	private String advertise2;
	private String site2;
	private String title2;
	private String title3;
	private String site3;
	private String advertise3;
	private String title4;
	private String site4;
	private String advertise4;

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
					"internationalization/advertise/Advertise");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/advertise/Advertise");
		}
	}

	public String getAdvertise1() {
		advertise1 = internationalizationService
				.getMessage(EAdvertise.ADEVRTISE1.getName());
		return advertise1;
	}

	public String getSite1() {
		site1 = internationalizationService.getMessage(EAdvertise.SITE1
				.getName());
		return site1;
	}

	public String getTitle1() {
		title1 = internationalizationService.getMessage(EAdvertise.TITLE1
				.getName());
		return title1;
	}

	public String getAdvertise2() {
		advertise2 = internationalizationService
				.getMessage(EAdvertise.ADEVRTISE2.getName());
		return advertise2;
	}

	public String getSite2() {
		site2 = internationalizationService.getMessage(EAdvertise.SITE2
				.getName());
		return site2;
	}

	public String getTitle2() {
		title2 = internationalizationService.getMessage(EAdvertise.TITLE2
				.getName());
		return title2;
	}

	public String getTitle3() {
		title3 = internationalizationService.getMessage(EAdvertise.TITLE3
				.getName());
		return title3;
	}

	public String getSite3() {
		site3 = internationalizationService.getMessage(EAdvertise.SITE3
				.getName());
		return site3;
	}

	public String getAdvertise3() {
		advertise3 = internationalizationService
				.getMessage(EAdvertise.ADEVRTISE3.getName());
		return advertise3;
	}

	public String getTitle4() {
		title4 = internationalizationService.getMessage(EAdvertise.TITLE4
				.getName());
		return title4;
	}

	public String getSite4() {
		site4 = internationalizationService.getMessage(EAdvertise.SITE4
				.getName());
		return site4;
	}

	public String getAdvertise4() {
		advertise4 = internationalizationService
				.getMessage(EAdvertise.ADEVRTISE4.getName());
		return advertise4;
	}
}
