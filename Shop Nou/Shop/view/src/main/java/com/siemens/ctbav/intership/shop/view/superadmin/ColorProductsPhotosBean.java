package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.util.UsersRole;
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

	private boolean isEnglishSelected;

	private Map<Long, Long> m;

	@PostConstruct
	private void postConstruct() {
		this.host = confService.getHost();
		setLanguage();
		if (isEnglishSelected) {
			photo = "/resources/pproducts.jpg";
		} else {
			photo = "/resources/pproductsR.jpg";
		}
	}

	private void setLanguage() {
		m = new HashMap<Long, Long>();
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
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
		setLanguage();
		if (!isEnglishSelected) {
			Double b = (Double) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("RON");
			for (ProductColor pc : productList) {
				if (!m.containsKey(pc.getProduct().getId())) {
					pc.setProductMultiplyPrice(b);
					m.put(pc.getProduct().getId(), pc.getProduct().getId());
				}
			}
		}

	}

	public void showProduct(ProductColor product) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedProduct", product);
		setLanguage();
		if (!isEnglishSelected) {
			Double b = (Double) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("RON");
			product.setProductMultiplyPrice(b);
		}
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user") != null) {

			if (request.isUserInRole(UsersRole.ADMINISTRATOR.toString())) {
				NavigationUtils
						.redirect("/Shop4j/admin/genericProducts/colorProducts/photos/"
								+ product.getId());
			}
			if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR.toString())) {
				NavigationUtils
						.redirect("/Shop4j/superadmin/genericProducts/colorProducts/photos/"
								+ product.getId());
			}
		}
	}

	public double getReductionPrice(ProductColor p) {
		double r = p.getProduct().getPrice() - p.getProduct().getPrice()
				* p.getProduct().getReduction() / 100;
		DecimalFormat df = new DecimalFormat("#.#");
		r = Double.parseDouble(df.format(r));
		return r;
	}
}
