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
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.CategoryName;

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
		} else {
			if (selectedParent != selectedNode.getParent()) {
				uniqueCheckNulName(selectedNode, selectedParent, messages);
			}
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
		} else {
			CategoryName cn = (CategoryName) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("currentName");
			if (cn.getName().equalsIgnoreCase(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						messages.getString(ECategory.ERROR.getName()),
						messages.getString(ECategory.INVALIDNAME1.getName())));
			}
		}
		uniqueCheck(name, selectedNode, selectedParent, messages);
	}

	private void uniqueCheckNulName(TreeNode selectedNode,
			TreeNode selectedParent, ResourceBundle messages) {
		checkTheNameInTheOtherHierarchy(selectedNode, messages);
	}

	private void uniqueCheck(String name, TreeNode selectedNode,
			TreeNode selectedParent, ResourceBundle messages) {
		if (selectedParent.equals(selectedNode.getParent())) {
			checkTheNameInTheSameHierarchy(name, messages);
		} else {
			checkTheNameInTheOtherHierarchy(name, selectedNode, messages);
		}
	}

	@SuppressWarnings("unchecked")
	private void checkTheNameInTheOtherHierarchy(String name,
			TreeNode selectedNode, ResourceBundle messages) {
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("childsP");
		checkTheNewName(name, messages, allcats);

		checkTheOldNames(selectedNode, messages, allcats);
	}

	private void checkTheOldNames(TreeNode selectedNode,
			ResourceBundle messages, List<Category> allcats) {
		String language = getLanguage();
		List<CategoryName> selectedNames = ((Category) selectedNode.getData())
				.getNames();
		for (CategoryName category : selectedNames) {
			if (!category.getLanguage().getLanguage().equals(language))
				for (Category c : allcats) {
					List<CategoryName> cns = c.getNames();
					for (CategoryName cn : cns) {
						if (category.getName().equalsIgnoreCase(cn.getName())) {
							throw new ValidatorException(
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											messages.getString(ECategory.ERROR
													.getName())
													+ " - "
													+ cn.getName(),
											cn.getLanguage().getName()
													+ ": "
													+ messages
															.getString(ECategory.INVALIDNAME2
																	.getName())));
						}
					}
				}
		}
	}

	private void checkTheNewName(String name, ResourceBundle messages,
			List<Category> allcats) {
		for (Category c : allcats) {
			List<CategoryName> cns = c.getNames();
			for (CategoryName cn : cns) {
				if (name.equalsIgnoreCase(cn.getName())) {
					throw new ValidatorException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							messages.getString(ECategory.ERROR.getName())
									+ " - " + cn.getName(), cn.getLanguage()
									.getName()
									+ ": "
									+ messages.getString(ECategory.INVALIDNAME2
											.getName())));
				}
			}
		}
	}

	private String getLanguage() {
		boolean isEnglishSelected;
		String language;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;

		if (isEnglishSelected) {
			language = new String("en");
		} else {
			language = new String("ro");
		}
		return language;
	}

	@SuppressWarnings("unchecked")
	private void checkTheNameInTheOtherHierarchy(TreeNode selectedNode,
			ResourceBundle messages) {
		Category selectedCategory = ((Category) selectedNode.getData());
		List<CategoryName> selectedNames = selectedCategory.getNames();
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("childsP");
		for (CategoryName category : selectedNames) {
			for (Category c : allcats) {
				List<CategoryName> cns = c.getNames();
				for (CategoryName cn : cns) {
					if (category.getName().equalsIgnoreCase(cn.getName())) {
						throw new ValidatorException(
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										messages.getString(ECategory.ERROR
												.getName())
												+ " - "
												+ cn.getName(),
										cn.getLanguage().getName()
												+ ": "
												+ messages
														.getString(ECategory.INVALIDNAME2
																.getName())));
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void checkTheNameInTheSameHierarchy(String name,
			ResourceBundle messages) {
		List<Category> allcats = (List<Category>) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("childs");
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
