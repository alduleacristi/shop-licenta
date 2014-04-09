package com.siemens.ctbav.intership.shop.util.client;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.internationalization.enums.client.ESecurityData;

@FacesValidator("validatePassword")
public class PasswordValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {

		String password = value.toString();
		ResourceBundle messages = internationalization();

		UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
				.get("confirm");

		String retypePassword = uiInputConfirmPassword.getSubmittedValue()
				.toString();

		if (!password.equals(retypePassword)) {
			uiInputConfirmPassword.setValid(false);
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(ESecurityData.ERROR.getName()),
					messages.getString(ESecurityData.NOT_THE_SAME.getName())));
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
		ResourceBundle messages = ResourceBundle.getBundle(
				"internationalization/client/securityData/SecurityData",
				currentLocale);
		return messages;
	}

}
