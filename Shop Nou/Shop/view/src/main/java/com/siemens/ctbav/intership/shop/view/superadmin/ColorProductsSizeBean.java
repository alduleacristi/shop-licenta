package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.util.UsersRole;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@URLMappings(mappings = {
		@URLMapping(id = "colorProductsSize", pattern = "/superadmin/genericProducts/colorProducts/sizes/", viewId = "colorProductsSizes.xhtml"),
		@URLMapping(id = "colorProductsSizeUpdate", pattern = "/superadmin/updateStoc/", viewId = "products.xhtml"),
		@URLMapping(id = "colorProductsSizeAdmin", pattern = "/admin/genericProducts/colorProducts/sizes/", viewId = "colorProductsSizes.xhtml"),
		@URLMapping(id = "colorProductsSizeUpdateAdmin", pattern = "/admin/updateStoc/", viewId = "products.xhtml") })
@ManagedBean(name = "colorProductsSizeBean")
@ViewScoped
public class ColorProductsSizeBean implements Serializable {
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
			photo = "/resources/sproducts.jpg";
		} else {
			photo = "/resources/sproductsR.jpg";
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

	public String displaySizes(ProductColor p) {
		StringBuilder sb = new StringBuilder(25);
		List<ProductColorSize> pcs = p.getProductColorSize();
		for (ProductColorSize pc : pcs) {
			sb.append(pc.getSize().getSize() + " ");
		}
		return sb.toString();
	}

	public void showProduct(ProductColor product) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedProductSize", product);

		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user") != null) {
			if (request.isUserInRole(UsersRole.ADMINISTRATOR.toString())) {
				NavigationUtils
						.redirect("/Shop4j/admin/genericProducts/colorProducts/sizes/"
								+ product.getId());
			}
			if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR.toString())) {
				NavigationUtils
						.redirect("/Shop4j/superadmin/genericProducts/colorProducts/sizes/"
								+ product.getId());
			}
		}
	}

	public void showUpdateProduct(ProductColor product) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedProductSize", product);
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user") != null) {
			if (request.isUserInRole(UsersRole.ADMINISTRATOR.toString())) {
				NavigationUtils.redirect("/Shop4j/admin/updateStoc/"
						+ product.getId());
			}
			if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR.toString())) {
				NavigationUtils.redirect("/Shop4j/superadmin/updateStoc/"
						+ product.getId());
			}
		}
	}
}
