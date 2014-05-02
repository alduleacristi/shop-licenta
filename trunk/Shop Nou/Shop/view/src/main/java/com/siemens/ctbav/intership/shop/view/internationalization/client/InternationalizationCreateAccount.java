package com.siemens.ctbav.intership.shop.view.internationalization.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.ECreateAccount;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationCreateAccount")
@RequestScoped
public class InternationalizationCreateAccount implements Serializable {

	private static final long serialVersionUID = 2013636671192124342L;

	@EJB
	private InternationalizationService internationalizationService;

	private String header;
	private String succesMessage;
	private String step2Message;
	private String createAccount;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String finish;

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
					"internationalization/client/createAccount/CreateAccount");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/createAccount/CreateAccount");
		}
	}

	public String getHeader() {
		header = internationalizationService.getMessage(ECreateAccount.HEADER
				.getName());
		return header;
	}

	public String getSuccesMessage() {
		succesMessage = internationalizationService
				.getMessage(ECreateAccount.SUCCESS_MESSAGE.getName());
		return succesMessage;
	}

	public String getStep2Message() {
		step2Message = internationalizationService
				.getMessage(ECreateAccount.STEP2_MESSAGE.getName());

		return step2Message;
	}

	public String getCreateAccount() {
		createAccount = internationalizationService
				.getMessage(ECreateAccount.CREATE_ACCOUNT.getName());

		return createAccount;
	}

	public String getFirstName() {
		firstName = internationalizationService
				.getMessage(ECreateAccount.FIRST_NAME.getName());

		return firstName;
	}

	public String getLastName() {
		lastName = internationalizationService
				.getMessage(ECreateAccount.LAST_NAME.getName());

		return lastName;
	}

	public String getPhoneNumber() {
		phoneNumber = internationalizationService
				.getMessage(ECreateAccount.PHONE_NUMBER.getName());

		return phoneNumber;
	}

	public String getFinish() {
		finish = internationalizationService.getMessage(ECreateAccount.FINISH
				.getName());

		return finish;
	}

}
