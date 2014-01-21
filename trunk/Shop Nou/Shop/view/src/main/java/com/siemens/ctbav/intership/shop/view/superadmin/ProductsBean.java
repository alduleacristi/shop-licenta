package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.NodeSelectEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.service.superadmin.ProductService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@URLMappings(mappings = { @URLMapping(id = "products", pattern = "/superadmin/genericProducts/", viewId = "products.xhtml") })
@ManagedBean(name = "productBean")
@ViewScoped
public class ProductsBean implements Serializable {

	private static final long serialVersionUID = 1239364578317785990L;

	@EJB
	ProductService productService;

	private boolean selectedCategory;
	private List<Product> productList;
	private Product selectedProduct;

	public boolean isSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(boolean selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getSelectedProduct() {
		selectedProduct = (Product) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedProduct");
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedProduct", selectedProduct);
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());
		selectedCategory = true;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedCategory", selectedCategory);
		long id = ((Category) event.getTreeNode().getData()).getId();
		productList = productService.getGenericProductsByCategory(id);
	}

	public void delete(ActionEvent actionEvent) {
		selectedProduct = (Product) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedProduct");
		try {
			tryToDelete();
		} catch (ProductException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			NavigationUtils.addMessage(message);
		} finally {
			NavigationUtils.redirect("/Shop4j/superadmin/genericProducts");
		}
	}

	private void tryToDelete() throws ProductException {
		if (selectedProduct == null)
			throw new ProductException(
					"You have to select a product to delete!");
		productService.removeProduct(selectedProduct.getId());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Success!", "Product deleted!");
		NavigationUtils.addMessage(message);
	}
}
