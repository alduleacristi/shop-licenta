package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EGenericProduct;

@FacesValidator("validateProductName")
public class ProductNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		ResourceBundle messages = internationalization();

		String name = value.toString();

		if (name.length() < 3 || name.length() > 20 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(EGenericProduct.ERROR.getName()),
					messages.getString(EGenericProduct.LENGHT.getName())));
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
						"internationalization/superadmin/messages/genericProducts/GenericProducts",
						currentLocale);
		return messages;
	}

}
