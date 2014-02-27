package com.siemens.ctbav.intership.shop.util.superadmin.validations;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.TreeNode;

import com.siemens.ctbav.intership.shop.model.Category;

@FacesValidator("validateUpdateCategory")
public class CategoryUpdateNameValidate implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		String name = arg2.toString();

		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		TreeNode selectedParent = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedParent");

		selectedParent = checkRoot(selectedNode, selectedParent);
		checkCircularDependecy(selectedNode, selectedParent);

		if (name != "") {
			validateName(name, selectedNode, selectedParent);
		}

	}

	private TreeNode checkRoot(TreeNode selectedNode, TreeNode selectedParent) {
		if (selectedNode.getParent() == null) {
			if (selectedParent != null) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Invalid parent",
								"Cannot update the parent of the root. Refresh the page to deselect the parent"));
			}
		}
		return selectedParent;
	}

	private void checkCircularDependecy(TreeNode selectedNode,
			TreeNode selectedParent) {
		if (selectedParent != null) {
			if (isCircularDependency(selectedNode, selectedParent)) {
				throw new ValidatorException(
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Invalid parent",
								"Cannot update the parent of the category due to a cyrcular dependency. Refresh the page to deselect the new parent"));
			}
		}
	}

	private void validateName(String name, TreeNode selectedNode,
			TreeNode selectedParent) {
		if (name.length() < 3 || name.length() > 15 || name.equals(null)) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid name",
					"Length must be between 3 and 15 characters"));
		} else if (name.equalsIgnoreCase(((Category) selectedNode.getData())
				.getName())) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Invalid name",
					"It's the same name!"));
		}
		uniqueCheck(name, selectedParent);
	}

	@SuppressWarnings("unchecked")
	private void uniqueCheck(String name, TreeNode selectedNode) {
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("categories");
		Category cat = new Category(name, (Category) selectedNode.getData());
		if (allcats.contains(cat)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Invalid name",
							"The category's parent already has a child with the same name!!!"));
		}
	}

	private boolean isCircularDependency(TreeNode selectedNode,
			TreeNode selectedParent) {
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
}
