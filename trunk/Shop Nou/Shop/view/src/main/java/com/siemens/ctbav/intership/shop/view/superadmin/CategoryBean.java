package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.CategoryException;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.ECategory;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.CategoryService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.util.superadmin.validations.CategoryUpdateNameValidate;

@URLMappings(mappings = { @URLMapping(id = "categories", pattern = "/superadmin/categories/", viewId = "categories.xhtml") })
@ManagedBean(name = "categoryBean")
@RequestScoped
public class CategoryBean implements Serializable {

	private static final long serialVersionUID = -431371041294693487L;

	@EJB
	private CategoryService categoryService;

	@EJB
	private InternationalizationService internationalizationService;

	private TreeNode root;
	private TreeNode rootUpdate;
	private TreeNode selectedNode;
	private TreeNode selectedParent;
	private boolean selected;
	private String newName;
	private String photo;

	@PostConstruct
	private void init() {
		root = null;
		List<Category> categories = categoryService.getList();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("categories", categories);
		setTheRoot(categories);
		if (root == null)
			selected = true;
		setTheRootUpdate(categories);
		NavigationUtils.addSuccesMessage();

		internationalizationInit();
	}

	private void internationalizationInit() {
		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			photo = "/resources/categories.jpg";
			String language = new String("en");
			String country = new String("US");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/categories/Categories");
		} else {
			photo = "/resources/categoriesR.jpg";
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/categories/Categories");
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRootUpdate() {
		return rootUpdate;
	}

	public void setRootUpdate(TreeNode rootUpdate) {
		this.rootUpdate = rootUpdate;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getSelectedParent() {
		return selectedParent;
	}

	public void setSelectedParent(TreeNode selectedParent) {
		this.selectedParent = selectedParent;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getPhoto() {
		return photo;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedParent", event.getTreeNode().getParent());
		selectedNode = event.getTreeNode();
		selected = true;
	}

	public void onParentSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedParent", event.getTreeNode());
		selectedParent = event.getTreeNode();
	}

	public void delete(ActionEvent actionEvent) {
		selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		try {
			if (selectedNode == null) {
				throw new CategoryException(
						internationalizationService
								.getMessage(ECategory.NOCATEGORYSELECTED
										.getName()));
			}
			deleteCategory();
		} catch (CategoryException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(ECategory.ERROR
							.getName()), e.getMessage());
			NavigationUtils.addMessage(message);
		} finally {
			NavigationUtils.redirect("/Shop4j/superadmin/categories");
		}
	}

	private void deleteCategory() throws CategoryException {
		if (selectedNode.getParent() == null) {
			throw new CategoryException(
					internationalizationService
							.getMessage(ECategory.DONTDELETEROOT.getName()));
		}
		if (!selectedNode.isLeaf()) {
			changeParentOfTheChildren();
		}
		categoryRemoved();
	}

	private void changeParentOfTheChildren() throws CategoryException {
		TreeNode parent = selectedNode.getParent();
		List<TreeNode> children = new ArrayList<TreeNode>(
				selectedNode.getChildren());
		for (TreeNode child : children) {
			categoryService.updateCategoryParent(
					((Category) child.getData()).getId(),
					((Category) parent.getData()).getId());
		}
	}

	private void categoryRemoved() throws CategoryException {
		categoryService.removeCategory(((Category) selectedNode.getData())
				.getId());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(ECategory.SUCCESS
						.getName()),
				internationalizationService
						.getMessage(ECategory.CATEGORYDELETED.getName()));
		NavigationUtils.addMessage(message);
	}

	public void create(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		FacesMessage msg = null;
		try {
			msg = checkAndCreateCategory();
			create = true;
		} catch (CategoryException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(ECategory.ERROR
							.getName()), e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private FacesMessage checkAndCreateCategory() throws CategoryException {
		if (selectedNode == null && root != null) {
			throw new CategoryException(
					internationalizationService
							.getMessage(ECategory.CREATEPARENTEXCEPTION
									.getName()));
		}
		if (root == null) {
			categoryService.createRoot(newName);
		} else {
			categoryService.createCategory(newName,
					(Category) selectedNode.getData());
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(ECategory.SUCCESS
						.getName()),
				internationalizationService.getMessage(ECategory.CATEGORYADDED
						.getName()));
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("success", msg);
		return msg;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		selectedParent = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedParent");

		FacesMessage msg = null;
		try {
			msg = checkAndUpdateCategory();
			update = true;
		} catch (CategoryException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(ECategory.ERROR
							.getName()), e.getMessage());
			update = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("update", update);
		}
	}

	private FacesMessage checkAndUpdateCategory() throws CategoryException {
		FacesMessage msg = null;
		long idCategory = ((Category) selectedNode.getData()).getId();
		long idParent = -1;
		if (selectedParent != null) {
			idParent = ((Category) selectedParent.getData()).getId();
		}
		new CategoryUpdateNameValidate().validate(
				FacesContext.getCurrentInstance(), null, newName);
		if (selectedNode == null) {
			throw new CategoryException(
					internationalizationService
							.getMessage(ECategory.NOCATEGORYSELECTED.getName()));
		}
		if (newName.equals("")) {
			msg = keepOldName(msg, idCategory, idParent);
		} else {
			msg = setNewName(msg, idCategory, idParent);
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("success", msg);
		return msg;
	}

	private FacesMessage setNewName(FacesMessage msg, long idCategory,
			long idParent) throws CategoryException {
		if (selectedParent == null
				|| selectedParent.equals(selectedNode.getParent())) {
			categoryService.updateCategoryName(idCategory, newName);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(ECategory.SUCCESS
							.getName()),
					internationalizationService
							.getMessage(ECategory.CATEGORYSNAMEUPDATED
									.getName()));
		} else if (selectedParent != null) {
			categoryService.updateCategoryNameAndParent(idCategory, idParent,
					newName);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(ECategory.SUCCESS
							.getName()),
					internationalizationService
							.getMessage(ECategory.CATEGORYSNAMEANDPARENTUPDATED
									.getName()));
		}
		return msg;
	}

	private FacesMessage keepOldName(FacesMessage msg, long idCategory,
			long idParent) throws CategoryException {
		if (selectedParent == null
				|| selectedParent.equals(selectedNode.getParent())) {
			throw new CategoryException(
					internationalizationService
							.getMessage(ECategory.NOTHINGTOUPDATE.getName()));
		} else if (selectedParent != null) {
			categoryService.updateCategoryParent(idCategory, idParent);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(ECategory.SUCCESS
							.getName()),
					internationalizationService
							.getMessage(ECategory.CATEGORYSPARENTUPDATED
									.getName()));
		}
		return msg;
	}

	private void setTheRootUpdate(List<Category> categories) {
		List<TreeNode> treeNodesU = new ArrayList<TreeNode>();
		for (Category category : categories) {
			if (category.getCategory() == null) {
				rootUpdate = new DefaultTreeNode(category, null);
				expand(rootUpdate);
				treeNodesU.add(rootUpdate);
			} else {
				TreeNode node = new DefaultTreeNode(category, getParent(
						treeNodesU, category.getCategory().getId()));
				expand(node);
				treeNodesU.add(node);
			}
		}
	}

	private void setTheRoot(List<Category> categories) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();

		for (Category category : categories) {
			if (category.getCategory() == null) {
				root = new DefaultTreeNode(category, null);
				expand(root);
				treeNodes.add(root);
			} else {
				TreeNode node = new DefaultTreeNode(category, getParent(
						treeNodes, category.getCategory().getId()));
				expand(node);
				treeNodes.add(node);
			}
		}
	}

	private TreeNode getParent(List<TreeNode> treeNodes, long id) {
		for (TreeNode n : treeNodes) {
			if (((Category) n.getData()).getId() == id)
				return n;
		}
		return null;
	}

	private void expand(TreeNode treeNode) {
		if (treeNode.getParent() != null) {
			treeNode.getParent().setExpanded(true);
		}
	}
}
