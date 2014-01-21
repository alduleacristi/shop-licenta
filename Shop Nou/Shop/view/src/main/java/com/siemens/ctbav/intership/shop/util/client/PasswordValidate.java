package com.siemens.ctbav.intership.shop.util.client;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatePassword")
public class PasswordValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {

		String password = value.toString();
		
		UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
				.get("confirm");
		
		String retypePassword = uiInputConfirmPassword.getSubmittedValue()
				.toString();
		
		if (!password.equals(retypePassword)) {
			uiInputConfirmPassword.setValid(false);
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Retype password must match password.","Retype password must match password."));
		  }
	}

}
