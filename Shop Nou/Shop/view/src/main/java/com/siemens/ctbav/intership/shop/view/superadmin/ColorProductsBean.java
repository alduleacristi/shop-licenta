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
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorService;
import com.siemens.ctbav.intership.shop.service.superadmin.ProductService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@URLMappings(mappings = { @URLMapping(id = "colorProducts", pattern = "/superadmin/genericProducts/colorProducts/", viewId = "colorProducts.xhtml") })
@ManagedBean(name = "colorProductBean")
@ViewScoped
public class ColorProductsBean implements Serializable {
	private static final long serialVersionUID = -6557816462778489324L;

	@EJB
	ProductService productService;

	@EJB
	ColorProductService colorProductService;

	@EJB
	ColorService colorService;

	private boolean selectedCategory;
	private List<Product> productList;
	private Product selectedProduct;
	private ProductColor selectedProductColor;
	private Long idSelectedProductColor;
	private List<Color> allColors;
	private Long idSelectedColor;
	private Color selectedColor;

	@PostConstruct
	void init() {

	}

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
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public ProductColor getSelectedProductColor() {
		return selectedProductColor;
	}

	public void setSelectedProductColor(ProductColor selectedProductColor) {
		this.selectedProductColor = selectedProductColor;
	}

	public Long getIdSelectedProductColor() {
		return idSelectedProductColor;
	}

	public void setIdSelectedProductColor(Long idSelectedProductColor) {
		this.idSelectedProductColor = idSelectedProductColor;
		selectedProductColor = colorProductService
				.getProductById(idSelectedProductColor);
	}

	public List<Color> getAllColors() {
		return allColors;
	}

	public void setAllColors(List<Color> allColors) {
		this.allColors = allColors;
	}

	public Long getIdSelectedColor() {
		return idSelectedColor;
	}

	public void setIdSelectedColor(Long idSelectedColor) {
		this.idSelectedColor = idSelectedColor;
		selectedColor = colorService.getColorById(idSelectedColor);
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedNode", event.getTreeNode());
		selectedCategory = true;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedCategory", selectedCategory);
		allColors = colorService.getAllColors();
		updateListOfProducts();
	}

	public String displayColors(Product p) {
		StringBuilder sb = new StringBuilder(25);
		List<ProductColor> pcs = p.getProductColor();
		for (ProductColor pc : pcs) {
			sb.append(pc.getColor().getName() + " ");
		}
		return sb.toString();
	}

	public void add(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		FacesMessage msg = null;
		try {
			uniqueCheck();
			msg = addColor();
			create = true;
		} catch (ColorProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private FacesMessage addColor() {
		FacesMessage msg;
		colorProductService.addProductColor(selectedProduct, selectedColor);
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes",
				"Color added!");
		updateListOfProducts();
		updateSelectedProduct();
		return msg;
	}

	private void uniqueCheck() throws ColorProductException {
		if (selectedProduct == null || selectedColor == null)
			throw new ColorProductException("No product or no color selected!");
		List<ProductColor> pcs = selectedProduct.getProductColor();
		List<Color> colors = new ArrayList<Color>();
		for (ProductColor p : pcs) {
			if (!colors.contains(p.getColor()))
				colors.add(p.getColor());
		}
		if (colors.contains(selectedColor))
			throw new ColorProductException("Coloe already exists!");
	}

	public void edit(ActionEvent actionEvent) {

	}

	public void delete(ActionEvent actionEvent) {
		FacesMessage msg = null;
		try {
			msg = tryToDelete();
		} catch (ColorProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
		} finally {
			NavigationUtils.addMessage(msg);
		}

	}

	private FacesMessage tryToDelete() throws ColorProductException {
		FacesMessage msg;
		if (selectedProductColor == null)
			throw new ColorProductException("No color was selected");
		colorProductService.removeProductColor(selectedProductColor.getId());
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes",
				"Color successfully deleted!");

		updateListOfProducts();

		updateSelectedProduct();
		return msg;
	}

	private void updateSelectedProduct() {
		selectedProduct.setProductColor(colorProductService
				.getColorsForProduct(selectedProduct.getId()));
		setSelectedProduct(selectedProduct);

		selectedProductColor = null;
		idSelectedProductColor = null;
	}

	private void updateListOfProducts() {
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		long id = ((Category) selectedNode.getData()).getId();
		productList = productService.getGenericProductsByCategory(id);
		for (Product product : productList) {
			product.setProductColor(colorProductService
					.getColorsForProduct(product.getId()));
		}
	}

}
