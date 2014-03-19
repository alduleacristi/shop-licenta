package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

@ManagedBean(name = "treeBeanVisitor")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "idVisitorProduct", pattern = "/client/category/#{treeBean.id}", viewId = "/client/products.xhtml"),
		@URLMapping(id = "idVisitorIndex", pattern = "/index", viewId = "/index.xhtml") })
public class TreeBeanVisitor implements Serializable {

	private static final long serialVersionUID = -6611396480967131619L;

	@EJB
	TreeService treeService;

	@ManagedProperty(value = "#{id}")
	private long id;

	private TreeNode root;

	private List<TreeNode> treeNodes;
	private List<Category> categories;

	private TreeNode selectedNode;
	private TreeNode selectedParent;
	private TreeNode selectedCategory;

	public TreeBeanVisitor() {
		selectedNode = null;
		selectedParent = null;
	}

	@PostConstruct
	private void init() {
		categories = treeService.getList();

		treeNodes = new ArrayList<TreeNode>();

		for (Category category : categories) {
			if (category.getCategory() == null) {
				root = new DefaultTreeNode(category, null);
				expand(root);
				treeNodes.add(root);
			} else {
				TreeNode node = new DefaultTreeNode(category,
						getParent(category.getCategory().getId()));
				expand(node);
				treeNodes.add(node);
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		if (id != 0) {
			Category selectedCategory = treeService.getCategory(id);
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("category", selectedCategory);
		}
	}

	public TreeNode getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(TreeNode selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public TreeNode getSelectedParent() {
		return selectedParent;
	}

	public void setSelectedParent(TreeNode selectedParent) {
		this.selectedParent = selectedParent;
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

	protected void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {
		Category category = ((Category) event.getTreeNode().getData());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("category", category);
		redirect("/Shop4j/client/category/" + category.getId());
	}
	

	private void expand(TreeNode treeNode) {
		if (treeNode.getParent() != null) {
			treeNode.getParent().setExpanded(true);
		}
	}
}
