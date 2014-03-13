package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
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
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ProductService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EGenericProduct;

@URLMappings(mappings = { @URLMapping(id = "manageSales", pattern = "/superadmin/manageSales/", viewId = "sales.xhtml") })
@ManagedBean(name = "manageSalesBean")
@ViewScoped
public class ManageSalesBean implements Serializable {

	private static final long serialVersionUID = -1406206209766385886L;

	@EJB
	ProductService productService;

	@EJB
	private InternationalizationService internationalizationService;

	private List<Product> productList;
	private Product selectedProduct;
	private boolean selectedCategory;
	private String photo;

	@PostConstruct
	void init() {
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
			photo = "/resources/sales.jpg";
			String language = new String("en");
			String country = new String("US");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		} else {
			photo = "/resources/salesR.jpg";
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		}
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public boolean isSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(boolean selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public String getPhoto() {
		return photo;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());
		long id = ((Category) event.getTreeNode().getData()).getId();
		productList = productService.getGenericProductsByCategory(id);
		selectedCategory = true;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;

		try {
			if (selectedProduct.getReduction() < 0
					|| selectedProduct.getReduction() > 100)
				throw new ProductException("Error sale!");
			productService.setSale(selectedProduct.getId(),
					selectedProduct.getReduction());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService
							.getMessage(EGenericProduct.SUCCESS.getName()),
					internationalizationService
							.getMessage(EGenericProduct.SUCCESS.getName()));
			update = true;
			updateList();
		} catch (ProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EGenericProduct.ERROR.getName()),
					e.getMessage());
			update = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("update", update);
		}

	}

	private void updateList() {
		TreeNode node = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		long id = ((Category) node.getData()).getId();
		productList = productService.getGenericProductsByCategory(id);
	}

}
