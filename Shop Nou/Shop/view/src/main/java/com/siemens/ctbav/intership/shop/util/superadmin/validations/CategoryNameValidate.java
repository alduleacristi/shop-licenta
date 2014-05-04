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

import org.primefaces.model.TreeNode;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.ECategory;
/*
 import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertCategory;
 import com.siemens.ctbav.intership.shop.dto.superadmin.CategoryDTO;*/
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.CategoryName;

@FacesValidator("validateCategory")
public class CategoryNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {
		String name = value.toString();

		ResourceBundle messages = internationalization();

		if (name.length() < 3 || name.length() > 15 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(ECategory.ERROR.getName()),
					messages.getString(ECategory.LENGTH.getName())));
		}

		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		if (selectedNode != null) {
			uniqueCheck(name, selectedNode, messages);
		}
	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name, TreeNode selectedNode,
			ResourceBundle messages) {
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("kids");
		for (Category c : allcats) {
			List<CategoryName> cns = c.getNames();
			for (CategoryName cn : cns) {
				if (name.equalsIgnoreCase(cn.getName())) {
					throw new ValidatorException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							messages.getString(ECategory.ERROR.getName())
									+ " - " + name, cn.getLanguage().getName()
									+ ": "
									+ messages.getString(ECategory.INVALIDNAME2
											.getName())));
				}
			}
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
						"internationalization/superadmin/messages/categories/Categories",
						currentLocale);
		return messages;
	}
}
