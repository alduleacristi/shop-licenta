package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.view.visitor.TreeBeanVisitor;

@ManagedBean(name = "treeBean")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "idClientProduct", pattern = "/client/user/category/#{treeBean.id}", viewId = "/client/user/products.xhtml"),
		@URLMapping(id = "idClientIndex", pattern = "/client/user/index", viewId = "/client/user/index.xhtml") })
public class TreeBean extends TreeBeanVisitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7831778859698176741L;

	@Override
	public void onNodeSelect(NodeSelectEvent event) {
		Category category = ((Category) event.getTreeNode().getData());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("category", category);
		redirect("/Shop4j/client/user/category/" + category.getId());
	}

}
