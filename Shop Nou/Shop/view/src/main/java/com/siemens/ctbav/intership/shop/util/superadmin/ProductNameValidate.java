package com.siemens.ctbav.intership.shop.util.superadmin;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validateProductName")
public class ProductNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		String name = value.toString();

		if (name.length() < 3 || name.length() > 20 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid name",
					"Length must be between 3 and 20 characters"));
		}

	}

}
