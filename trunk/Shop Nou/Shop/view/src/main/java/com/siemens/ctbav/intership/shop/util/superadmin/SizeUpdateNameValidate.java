package com.siemens.ctbav.intership.shop.util.superadmin;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertSize;
import com.siemens.ctbav.intership.shop.dto.superadmin.SizeDTO;
import com.siemens.ctbav.intership.shop.model.Size;

@FacesValidator("validateUpdateSize")
public class SizeUpdateNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		String name = value.toString();
		if (name != "") {
			if (name.length() < 1 || name.length() > 5 || name.equals(null)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Invalid name",
						"Length must be between 1 and 5 characters"));
			}
			uniqueCheck(name);
		} else
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Nothing to update",
					"Name not set"));
	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name) {

		SizeDTO search = new SizeDTO(name, null);
		Size selectedSize = (Size) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedSize");

		if (selectedSize.getSize().equalsIgnoreCase(name))
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Nothing to update",
					"It's the same size as before"));

		List<SizeDTO> allSizes = ConvertSize
				.convertSizes((List<Size>) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("allSizes"));

		if (allSizes.contains(search)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid name",
					"The category already has a size with the same name"));
		}
	}

}
