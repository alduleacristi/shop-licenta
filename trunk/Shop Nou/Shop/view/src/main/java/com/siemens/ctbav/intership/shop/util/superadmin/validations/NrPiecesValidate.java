package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EColorSizeProducts;

@FacesValidator("validatePieces")
public class NrPiecesValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		ResourceBundle messages = internationalization();
		if (arg2 != null) {
			String nr = arg2.toString();
			Pattern pattern = Pattern.compile("^[0-9]+$");
			Matcher matcher = pattern.matcher(nr);
			if (!matcher.matches()) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						messages.getString(EColorSizeProducts.ERROR.getName()),
						"\""
								+ nr
								+ "\" "
								+ messages.getString(EColorSizeProducts.INVALID
										.getName())));
			}
		} else {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(EColorSizeProducts.ERROR.getName()),
					"\""
							+ "\" "
							+ messages.getString(EColorSizeProducts.INVALID
									.getName())));
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
						"internationalization/superadmin/messages/colorSizeProducts/ColorSizeProducts",
						currentLocale);
		return messages;
	}

}
