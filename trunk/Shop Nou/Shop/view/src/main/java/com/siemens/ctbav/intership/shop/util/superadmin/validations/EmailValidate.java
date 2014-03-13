package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.view.internationalization.enums.EManageUsers;

@FacesValidator("emailValidate")
public class EmailValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		ResourceBundle messages = internationalization();

		String email = arg2.toString();
		String regexEmail = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";

		if (!email.matches(regexEmail)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(EManageUsers.ERROR.getName()),
					messages.getString(EManageUsers.INVALID_EMAIL.getName())));
		}
	}

	private ResourceBundle internationalization() {
		String language;
		String country;

		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;

		if (isEnglishSelected) {
			language = new String("en");
			country = new String("US");
		} else {
			language = new String("ro");
			country = new String("RO");
		}

		Locale currentLocale = new Locale(language, country);
		ResourceBundle messages = ResourceBundle
				.getBundle(
						"internationalization/superadmin/messages/manageUsers/ManageUsers",
						currentLocale);
		return messages;
	}

}
