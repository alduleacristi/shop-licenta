package com.siemens.ctbav.intership.shop.view.internationalization;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EMenuIndex;

@ManagedBean(name = "internationalizationBean")
@SessionScoped
public class InternationalizationBean {

	@EJB
	private InternationalizationService internationalizationService;

	private String menuIndexHome;
	private String menuIndexLogout;
	private boolean isEnglishSelected;
	private String language;
	private String country;
	private String changeLanguage;
	private String flag;

	@PostConstruct
	private void init() {
		isEnglishSelected = true;
		language = new String("en");
		country = new String("US");
		internationalizationService.setCurrentLocale(language, country,
				"internationalization/superadmin/menu/menuIndex/MenuIndex");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("isEnglishSelected", isEnglishSelected);
		changeLanguage = "Romana";
		flag = "/resources/romanian_flag.jpg";
	}

	public String getMenuIndexHome() {
		menuIndexHome = internationalizationService.getMessage(EMenuIndex.HOME
				.getName());
		return menuIndexHome;
	}

	public String getMenuIndexLogout() {
		menuIndexLogout = internationalizationService
				.getMessage(EMenuIndex.LOGOUT.getName());
		return menuIndexLogout;
	}

	public boolean isEnglishSelected() {
		return isEnglishSelected;
	}

	public void setEnglishSelected(boolean isEnglishSelected) {
		this.isEnglishSelected = isEnglishSelected;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("isEnglishSelected", isEnglishSelected);
		if (isEnglishSelected == true) {
			language = new String("en");
			country = new String("US");
			changeLanguage = "Romana";
			flag = "/resources/romanian_flag.jpg";
		} else {
			language = new String("ro");
			country = new String("RO");
			changeLanguage = "English";
			flag = "/resources/us-flag.png";
		}
		internationalizationService.setCurrentLocale(language, country,
				"internationalization/superadmin/menu/menuIndex/MenuIndex");
	}

	public String getChangeLanguage() {
		return changeLanguage;
	}

	public void setChangeLanguage(String changeLanguage) {
		this.changeLanguage = changeLanguage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void change(ActionEvent actionEvent) {
		if (changeLanguage.equals("Romana")) {
			setEnglishSelected(false);
			flag = "/resources/us-flag.png";
			changeLanguage = "English";
		} else {
			flag = "/resources/romanian_flag.jpg";
			changeLanguage = "Romana";
			setEnglishSelected(true);
		}
	}
}
