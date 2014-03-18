package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@URLMappings(mappings = {
		@URLMapping(id = "colorProductsPhotos", pattern = "/superadmin/genericProducts/colorProducts/photos/", viewId = "photos.xhtml"),
		@URLMapping(id = "colorProductsPhotosAdmin", pattern = "/admin/genericProducts/colorProducts/photos/", viewId = "photos.xhtml") })
@ManagedBean(name = "colorProductPhotosBean")
@ViewScoped
public class ColorProductsPhotosBean implements Serializable {
	private static final long serialVersionUID = -7715800049046729621L;

	@EJB
	ColorProductService colorProductService;

	@EJB
	private ConfigurationService confService;

	private boolean selectedCategory;
	private List<ProductColor> productList;
	private String host;
	private String photo;

	@PostConstruct
	private void postConstruct() {
		this.host = confService.getHost();

		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			photo = "/resources/pproducts.jpg";
		} else {
			photo = "/resources/pproductsR.jpg";
		}
	}

	public boolean isSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(boolean selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<ProductColor> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductColor> productList) {
		this.productList = productList;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPhoto() {
		return photo;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());
		selectedCategory = true;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedCategory", selectedCategory);
		updateListOfProducts();
	}

	private void updateListOfProducts() {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		long id = ((Category) selectedNode.getData()).getId();
		productList = colorProductService.getProductsByCategory(id);
	}

	public void showProduct(ProductColor product) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedProduct", product);
		NavigationUtils
				.redirect("/Shop4j/superadmin/genericProducts/colorProducts/photos/"
						+ product.getId());
	}
}
