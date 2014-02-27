package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatePieces")
public class NrPiecesValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		if (arg2 != null) {
			String nr = arg2.toString();
			Pattern pattern = Pattern.compile("^[0-9]+$");
			Matcher matcher = pattern.matcher(nr);
			if (!matcher.matches()) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Invalid number", "\""
								+ nr + "\"" + " is not a valid number"));
			}
		} else {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid number", "\"" + "\""
							+ " is not a valid number"));
		}

	}

}
