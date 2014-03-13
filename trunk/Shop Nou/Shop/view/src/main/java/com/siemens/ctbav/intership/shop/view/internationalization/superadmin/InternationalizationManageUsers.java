package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EManageUsers;

@ManagedBean(name = "internationalizationManageUsers")
@RequestScoped
public class InternationalizationManageUsers implements Serializable {

	private static final long serialVersionUID = -1539289596311208991L;

	@EJB
	private InternationalizationService internationalizationService;

	private String listA;
	private String username;
	private String detailA;
	private String m1;
	private String m2;
	private String m3;
	private String m4;
	private String password;
	private String retype;
	private String listO;
	private String detailO;
	private String requiredUsername;
	private String requiredEmail;
	private String requiredPassword;

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
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		}
	}

	public String getListA() {
		listA = internationalizationService.getMessage(EManageUsers.LIST_A
				.getName());
		return listA;
	}

	public String getUsername() {
		username = internationalizationService.getMessage(EManageUsers.USER
				.getName());
		return username;
	}

	public String getDetailA() {
		detailA = internationalizationService.getMessage(EManageUsers.DETAIL_A
				.getName());
		return detailA;
	}

	public String getM1() {
		m1 = internationalizationService.getMessage(EManageUsers.M1.getName());
		return m1;
	}

	public String getM2() {
		m2 = internationalizationService.getMessage(EManageUsers.M2.getName());
		return m2;
	}

	public String getM3() {
		m3 = internationalizationService.getMessage(EManageUsers.M3.getName());
		return m3;
	}

	public String getM4() {
		m4 = internationalizationService.getMessage(EManageUsers.M4.getName());
		return m4;
	}

	public String getPassword() {
		password = internationalizationService.getMessage(EManageUsers.PASSWORD
				.getName());
		return password;
	}

	public String getRetype() {
		retype = internationalizationService.getMessage(EManageUsers.RETYPE
				.getName());
		return retype;
	}

	public String getListO() {
		listO = internationalizationService.getMessage(EManageUsers.LIST_O
				.getName());
		return listO;
	}

	public String getDetailO() {
		detailO = internationalizationService.getMessage(EManageUsers.DETAIL_O
				.getName());
		return detailO;
	}

	public String getRequiredUsername() {
		requiredUsername = internationalizationService
				.getMessage(EManageUsers.REQUIRED_USER.getName());
		return requiredUsername;
	}

	public String getRequiredEmail() {
		requiredEmail = internationalizationService
				.getMessage(EManageUsers.REQUIRED_EMAIL.getName());
		return requiredEmail;
	}

	public String getRequiredPassword() {
		requiredPassword = internationalizationService
				.getMessage(EManageUsers.REQUIRED_PASSWORD.getName());
		return requiredPassword;
	}
}
