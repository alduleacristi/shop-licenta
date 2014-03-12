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

import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.ECategory;

@FacesValidator("validateUpdateCategory")
public class CategoryUpdateNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		String name = arg2.toString();

		ResourceBundle messages = internationalization();

		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		TreeNode selectedParent = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedParent");

		selectedParent = checkRoot(selectedNode, selectedParent, messages);
		checkCircularDependecy(selectedNode, selectedParent, messages);

		if (name != "") {
			validateName(name, selectedNode, selectedParent, messages);
		}

	}

	private TreeNode checkRoot(TreeNode selectedNode, TreeNode selectedParent,
			ResourceBundle messages) {
		if (selectedNode.getParent() == null) {
			if (selectedParent != null) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						messages.getString(ECategory.ERROR.getName()),
						messages.getString(ECategory.ERRORPARENT.getName())));
			}
		}
		return selectedParent;
	}

	private void checkCircularDependecy(TreeNode selectedNode,
			TreeNode selectedParent, ResourceBundle messages) {
		if (selectedParent != null) {
			if (isCircularDependency(selectedNode, selectedParent, messages)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						messages.getString(ECategory.ERROR.getName()),
						messages.getString(ECategory.CYRCULARD.getName())));
			}
		}
	}

	private void validateName(String name, TreeNode selectedNode,
			TreeNode selectedParent, ResourceBundle messages) {
		if (name.length() < 3 || name.length() > 15 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(ECategory.ERROR.getName()),
					messages.getString(ECategory.LENGTH.getName())));
		} else if (name.equalsIgnoreCase(((Category) selectedNode.getData())
				.getName())) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(ECategory.ERROR.getName()),
					messages.getString(ECategory.INVALIDNAME1.getName())));
		}
		uniqueCheck(name, selectedParent, messages);
	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name, TreeNode selectedNode,
			ResourceBundle messages) {
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("categories");
		Category cat = new Category(name, (Category) selectedNode.getData());
		if (allcats.contains(cat)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					messages.getString(ECategory.ERROR.getName()),
					messages.getString(ECategory.INVALIDNAME2.getName())));
		}
	}

	private boolean isCircularDependency(TreeNode selectedNode,
			TreeNode selectedParent, ResourceBundle messages) {
		TreeNode aux = selectedParent;
		if (selectedParent != null && selectedNode.equals(selectedParent))
			return true;
		while (aux.getParent() != null) {
			if (aux.getParent().equals(selectedNode)) {
				return true;
			}
			aux = aux.getParent();
		}
		return false;
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
