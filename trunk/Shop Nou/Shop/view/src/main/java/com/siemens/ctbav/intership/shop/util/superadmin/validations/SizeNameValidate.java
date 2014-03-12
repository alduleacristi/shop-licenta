package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertSize;
import com.siemens.ctbav.intership.shop.dto.superadmin.SizeDTO;
import com.siemens.ctbav.intership.shop.model.Size;

@FacesValidator("validateSize")
public class SizeNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {
		ResourceBundle messages = internationalization();

		String name = value.toString();
		if (name.length() < 1 || name.length() > 5 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, messages.getString("error"),
					messages.getString("length")));
		}

		uniqueCheck(name, messages);

	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name, ResourceBundle messages) {
		SizeDTO search = new SizeDTO(name, null);

		List<SizeDTO> allSizes = ConvertSize
				.convertSizes((List<Size>) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("allSizes"));

		if (allSizes.contains(search)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, messages.getString("error"),
					messages.getString("sizeExceptionUnique")));
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
				"internationalization/superadmin/messages/sizes/Sizes",
				currentLocale);
		return messages;
	}
}
