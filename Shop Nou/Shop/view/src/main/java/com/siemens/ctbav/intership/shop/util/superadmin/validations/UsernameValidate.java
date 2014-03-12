package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validateUsername")
public class UsernameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		String name = value.toString();

		if (name.length() < 3 || name.length() > 15 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid username",
					"Length must be between 3 and 15 characters"));
		}
	}

}
