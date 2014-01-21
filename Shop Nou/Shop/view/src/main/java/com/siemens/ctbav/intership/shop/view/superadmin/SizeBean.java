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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.SizeException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Size;
import com.siemens.ctbav.intership.shop.service.superadmin.SizeService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.util.superadmin.SizeUpdateNameValidate;
import com.siemens.ctbav.intership.shop.util.superadmin.selectable.SelectableSize;

@ManagedBean(name = "sizeBean")
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "sizes", pattern = "/superadmin/categories/sizes/", viewId = "sizes.xhtml") })
public class SizeBean implements Serializable {

	private static final long serialVersionUID = -6315825131536491592L;

	@EJB
	private SizeService sizeService;

	private String newName;
	private boolean addSize;
	private List<Size> parentsSizes;
	private Size selectedSize;
	private SelectableSize sizes;

	@PostConstruct
	void init() {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		if (selectedNode != null) {
			initAddAndSelectedSize(selectedNode);
		}
	}

	private void initAddAndSelectedSize(TreeNode selectedNode) {
		long idSelectedNode = ((Category) selectedNode.getData()).getId();
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("addSize");
		if (b != null)
			addSize = b;
		else
			addSize = false;
		if (addSize == true) {
			initSelectedSize(idSelectedNode);
		}
	}

	private void initSelectedSize(long idSelectedNode) {
		List<Size> currentSizes = sizeService
				.getSizesByCategory(idSelectedNode);
		parentsSizes = sizeService
				.storedProcedureGetParentsSizes(idSelectedNode);
		sizes = new SelectableSize(currentSizes);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("addSize");
		selectedSize = (Size) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedSize");
		System.out.println("in post construct: " + selectedSize);
		putAllSizes(currentSizes);
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public boolean isAddSize() {
		return addSize;
	}

	public void setAddSize(boolean addSize) {
		this.addSize = addSize;
	}

	public List<Size> getParentsSizes() {
		return parentsSizes;
	}

	public void setParentsSizes(List<Size> parentsSizes) {
		this.parentsSizes = parentsSizes;
	}

	public Size getSelectedSize() {
		return selectedSize;
	}

	public void setSelectedSize(Size selectedSize) {
		this.selectedSize = selectedSize;
	}

	public SelectableSize getSizes() {
		return sizes;
	}

	public void setSizes(SelectableSize sizes) {
		this.sizes = sizes;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());

		TreeNode selectedNode = event.getTreeNode();
		long idSelectedNode = ((Category) selectedNode.getData()).getId();

		setEditableSize(selectedNode, idSelectedNode);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("addSize", addSize);
	}

	public void onRowSelect(SelectEvent event) {
		selectedSize = (Size) event.getObject();
		System.out.println("On row select: " + selectedSize);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedSize", selectedSize);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("addSize", addSize);
	}

	private void setEditableSize(TreeNode selectedNode, long idSelectedNode) {
		if (selectedNode.getParent() != null) {
			if (selectedNode.getParent().getParent() != null) {
				addSize = true;
				List<Size> currentSizes = sizeService
						.getSizesByCategory(idSelectedNode);
				parentsSizes = sizeService
						.storedProcedureGetParentsSizes(idSelectedNode);
				sizes = new SelectableSize(currentSizes);
				putAllSizes(currentSizes);
			} else
				addSize = false;
		} else
			addSize = false;
	}

	private void putAllSizes(List<Size> currentSizes) {
		List<Size> allSizes = new ArrayList<Size>();
		allSizes.addAll(currentSizes);
		allSizes.addAll(parentsSizes);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("allSizes");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("allSizes", allSizes);
	}

	public void delete(ActionEvent actionEvent) {
		selectedSize = (Size) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedSize");
		try {
			tryToDelete();
		} catch (SizeException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			NavigationUtils.addMessage(message);
		} finally {
			NavigationUtils
					.redirect("/Shop4j/superadmin/categories/sizes");
		}
	}

	private void tryToDelete() throws SizeException {
		if (selectedSize == null)
			throw new SizeException("You have to select a size to delete!");
		sizeService.removeSize(selectedSize.getId());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Success!", "Size deleted!");
		NavigationUtils.addMessage(message);
	}

	public void create(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		FacesMessage msg = null;
		try {
			msg = tryToCreate();
			create = true;
		} catch (SizeException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private FacesMessage tryToCreate() throws SizeException {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		if (selectedNode == null)
			throw new SizeException(
					"You have to select a category in order to add a size");
		sizeService.createSize(newName, (Category) selectedNode.getData());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Succes", "New size added");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("success", msg);
		return msg;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;
		try {
			msg = tryToUpdate();
			update = true;
		} catch (SizeException e) {
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			update = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("update", update);
		}

	}

	private FacesMessage tryToUpdate() throws SizeException {
		treatExceptionsUpdate();
		long id = selectedSize.getId();
		new SizeUpdateNameValidate().validate(
				FacesContext.getCurrentInstance(), null, newName);
		sizeService.updateSize(id, newName);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Succes", "Size edited");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("success", msg);
		return msg;
	}

	private void treatExceptionsUpdate() throws SizeException {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		selectedSize = (Size) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedSize");
		if (selectedNode == null)
			throw new SizeException("No category selected");
		if (selectedSize == null)
			throw new SizeException("No size selected");
	}
}
