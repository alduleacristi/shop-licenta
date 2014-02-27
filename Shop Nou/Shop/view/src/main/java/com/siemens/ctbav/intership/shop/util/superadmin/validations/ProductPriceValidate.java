package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validateProductPrice")
public class ProductPriceValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		String price = value.toString();
		float priceF = Float.parseFloat(price);
		if (priceF <= 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid price",
					"The price must be positive"));
		}

	}

}
