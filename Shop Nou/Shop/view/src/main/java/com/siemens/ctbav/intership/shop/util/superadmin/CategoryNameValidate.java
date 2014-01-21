package com.siemens.ctbav.intership.shop.util.superadmin;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.TreeNode;

import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertCategory;
import com.siemens.ctbav.intership.shop.dto.superadmin.CategoryDTO;
import com.siemens.ctbav.intership.shop.model.Category;

@FacesValidator("validateCategory")
public class CategoryNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {
		String name = value.toString();

		if (name.length() < 3 || name.length() > 15 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid name",
					"Length must be between 3 and 15 characters"));
		}

		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		if (selectedNode != null) {
			uniqueCheck(name, selectedNode);
		}
	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name, TreeNode selectedNode) {
		CategoryDTO search = new CategoryDTO(name,
				ConvertCategory.convertCategory((Category) selectedNode
						.getData()));
		List<CategoryDTO> allCategories = ConvertCategory
				.convertCategories((List<Category>) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSessionMap().get("categories"));

		if (allCategories.contains(search)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Invalid name",
							"The category's parent already has a child with the same name"));
		}
	}
}
