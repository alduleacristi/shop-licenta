package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import com.siemens.ctbav.intership.shop.util.superadmin.selectable.SelectableSize;

@ManagedBean(name = "sizeBean")
@ViewScoped
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
	}

	private void initSelectedSize(long idSelectedNode) {
		List<Size> currentSizes = sizeService
				.getSizesByCategory(idSelectedNode);
		parentsSizes = sizeService
				.storedProcedureGetParentsSizes(idSelectedNode);
		sizes = new SelectableSize(currentSizes);
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
		initSelectedSize(idSelectedNode);
	}

	private void updateSizes() {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		long idSelectedNode = ((Category) selectedNode.getData()).getId();

		setEditableSize(selectedNode, idSelectedNode);
		initSelectedSize(idSelectedNode);
		selectedSize = null;
	}

	public void onRowSelect(SelectEvent event) {
		selectedSize = (Size) event.getObject();

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedSize", selectedSize);
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
		try {
			tryToDelete();
			updateSizes();
		} catch (SizeException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			NavigationUtils.addMessage(message);
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
			updateSizes();
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
		return msg;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;
		try {
			msg = tryToUpdate();
			update = true;
			updateSizes();
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
		sizeService.updateSize(id, selectedSize.getSize());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Succes", "Size edited");
		return msg;
	}

	private void treatExceptionsUpdate() throws SizeException {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		if (selectedNode == null)
			throw new SizeException("No category selected");
		if (selectedSize == null)
			throw new SizeException("No size selected");
	}
}
