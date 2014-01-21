package com.siemens.ctbav.intership.shop.view.client;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.service.client.TreeService;


@ManagedBean(name = "treeBean")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "idClientProduct", pattern = "/client/user/category/#{treeBean.id}", viewId = "/client/user/products.xhtml"),
		@URLMapping(id = "idClientIndex", pattern = "/client/user/index", viewId = "/client/user/index.xhtml")
})

public class TreeBean implements Serializable {

	@ManagedProperty(value = "#{id}")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		System.out.println("se seteaza id-ul pt mapare in treeBean" + id);
		this.id = id;
		if (id != 0) {
			Category selectedCategory = treeService.getCategory(id);
			System.out
					.println("punem categoria: " + selectedCategory.getName());
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("category", selectedCategory);
		}
	}

	private static final long serialVersionUID = -6611396480967131619L;
	@EJB
	TreeService treeService;

	private TreeNode root;

	private List<TreeNode> treeNodes;
	private List<Category> categories;

	private TreeNode selectedNode;
	private TreeNode selectedParent;
	private TreeNode selectRead;
	private TreeNode selectedCategory;

	private String name;

	private String nameUpdate;

	public TreeBean() {
		selectedNode = null;
		selectedParent = null;
		selectRead = null;
		name = null;
		nameUpdate = null;
	}

	@PostConstruct
	private void init() {
		categories = treeService.getList();

		treeNodes = new ArrayList<TreeNode>();

		for (Category category : categories) {
			if (category.getCategory() == null) {
				root = new DefaultTreeNode(category, null);
				treeNodes.add(root);
			} else {
				TreeNode node = new DefaultTreeNode(category,
						getParent(category.getCategory().getId()));
				treeNodes.add(node);
			}
		}
	}

	public TreeNode getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(TreeNode selectedCategory) {
		// System.out.println("selected category");
		this.selectedCategory = selectedCategory;
		// redirect("cateory.xhtml");
	}

	public void onNodeSelect(NodeSelectEvent event) {
		Category category = ((Category) event.getTreeNode().getData());
		System.out.println("punem categoria in event: " + category.getName());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("category", category);
		redirect("/Shop4j/client/user/category/" + category.getId());
	}

	public TreeNode getSelectRead() {
		return selectRead;
	}

	public void setSelectRead(TreeNode selectRead) {
		this.selectRead = selectRead;
	}

	public TreeNode getSelectedParent() {
		return selectedParent;
	}

	public void setSelectedParent(TreeNode selectedParent) {
		this.selectedParent = selectedParent;
	}

	public String getNameUpdate() {
		return nameUpdate;
	}

	public void setNameUpdate(String nameUpdate) {
		this.nameUpdate = nameUpdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private TreeNode getParent(long id) {
		for (TreeNode n : treeNodes) {
			if (((Category) n.getData()).getId() == id)
				return n;
		}
		return null;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	private void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void back() {
		redirect("/Shop4J/admin/Categories");
	}

}
