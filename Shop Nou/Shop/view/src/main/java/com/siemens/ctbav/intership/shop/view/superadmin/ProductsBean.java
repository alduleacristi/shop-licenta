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
import com.siemens.ctbav.intership.shop.convert.superadmin.ConvertProduct;
import com.siemens.ctbav.intership.shop.dto.superadmin.CategoryDTO;
import com.siemens.ctbav.intership.shop.dto.superadmin.ProductDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ProductService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EGenericProduct;

@URLMappings(mappings = { @URLMapping(id = "products", pattern = "/superadmin/genericProducts/", viewId = "products.xhtml") })
@ManagedBean(name = "productBean")
@ViewScoped
public class ProductsBean implements Serializable {

	private static final long serialVersionUID = 1239364578317785990L;

	@EJB
	ProductService productService;

	@EJB
	private InternationalizationService internationalizationService;

	private boolean selectedCategory;
	private List<Product> productList;
	private Product selectedProduct;
	private ProductDTO newProduct;

	@PostConstruct
	void init() {
		newProduct = new ProductDTO();
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
			String language = new String("en");
			String country = new String("US");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/genericProducts/GenericProducts");
		}
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

	public ProductDTO getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(ProductDTO newProduct) {
		this.newProduct = newProduct;
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

	public void onNewParentSelect(NodeSelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("selectedParent", event.getTreeNode());
	}

	private void updateList() {
		TreeNode node = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		long id = ((Category) node.getData()).getId();
		productList = productService.getGenericProductsByCategory(id);
	}

	public void delete(ActionEvent actionEvent) {
		try {
			tryToDelete();
			updateList();
		} catch (ProductException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EGenericProduct.ERROR.getName()),
					e.getMessage());
			NavigationUtils.addMessage(message);
		}
	}

	private void tryToDelete() throws ProductException {
		if (selectedProduct == null)
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.SEL_PROD_ERR.getName()));
		productService.removeProduct(selectedProduct.getId());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(EGenericProduct.SUCCESS
						.getName()),
				internationalizationService
						.getMessage(EGenericProduct.PROD_DELETED.getName()));
		NavigationUtils.addMessage(message);
	}

	public void create(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		FacesMessage msg = null;
		try {
			msg = tryToCreate();
			create = true;
			updateList();
		} catch (ProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EGenericProduct.ERROR.getName()),
					e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private FacesMessage tryToCreate() throws ProductException {
		FacesMessage msg;
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");

		createExceptions(selectedNode);

		long idCategory = ((Category) selectedNode.getData()).getId();
		productService.createProduct(newProduct, idCategory);
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(EGenericProduct.SUCCESS
						.getName()),
				internationalizationService
						.getMessage(EGenericProduct.PROD_ADDED.getName()));
		return msg;
	}

	private void createExceptions(TreeNode selectedNode)
			throws ProductException {
		if (selectedNode == null)
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.NO_CATEG_SEL.getName()));

		List<ProductDTO> productsDto = ConvertProduct
				.convertProducts(productList);
		CategoryDTO c = new CategoryDTO(
				((Category) selectedNode.getData()).getName(), null);
		newProduct.setCategory(c);

		if (productsDto.contains(newProduct))
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.DUPLICATE_PROD
									.getName()));
	}

	public void changeCategory(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean change = false;
		FacesMessage msg = null;
		/*
		 * selectedProduct = (Product) FacesContext.getCurrentInstance()
		 * .getExternalContext().getSessionMap().get("selectedProduct");
		 */
		try {
			msg = tryToChangeParent();
			change = true;
		} catch (ProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EGenericProduct.ERROR.getName()),
					e.getMessage());
			change = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("change", change);
		}
	}

	private FacesMessage tryToChangeParent() throws ProductException {
		TreeNode selectedParent = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedParent");
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");

		updateParentExceptions(selectedNode, selectedParent);

		boolean isSon = checkRelationship(selectedParent, selectedNode);
		boolean isParent = checkRelationship(selectedNode, selectedParent);

		long idCategory = uniqueCheck(selectedParent, isSon, isParent);
		productService.changeParent(selectedProduct.getId(), idCategory);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(EGenericProduct.SUCCESS
						.getName()),
				internationalizationService
						.getMessage(EGenericProduct.CATEG_CHANGED.getName()));
		productList.clear();
		productList.addAll(productService
				.getGenericProductsByCategory(idCategory));
		return msg;
	}

	private void updateParentExceptions(TreeNode selectedNode,
			TreeNode selectedParent) throws ProductException {
		if (selectedNode == null) {
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.NO_CATEG_SEL.getName()));
		}
		if (selectedProduct == null) {
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.SEL_PROD_ERR.getName()));
		}
		if (selectedParent == null) {
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.NO_PARENT_SEL.getName()));
		}
	}

	private long uniqueCheck(TreeNode selectedParent, boolean isSon,
			boolean isParent) throws ProductException {
		long idCategory = ((Category) selectedParent.getData()).getId();
		if (!isSon && !isParent) {
			check(idCategory);
		}
		return idCategory;
	}

	private void check(long idCategory) throws ProductException {
		List<ProductDTO> productsDto = ConvertProduct
				.convertProducts(productService
						.getGenericProductsByCategory(idCategory));
		ProductDTO productDto = ConvertProduct.convertProduct(selectedProduct);

		if (productsDto.contains(productDto)) {
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.DUPLICATE_PROD
									.getName()));
		}
	}

	private boolean checkRelationship(TreeNode selectedParent,
			TreeNode selectedNode) {
		boolean isSon = false;
		TreeNode parent = selectedNode;
		while (parent != null) {
			if (parent.equals(selectedParent)) {
				isSon = true;
				break;
			}
			parent = parent.getParent();
		}
		return isSon;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;

		try {
			msg = tryToUpdate();
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

	private FacesMessage tryToUpdate() throws ProductException {
		FacesMessage msg;
		TreeNode selectedNode = (TreeNode) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode");
		Product oldProduct = updateFieldsExceptions(selectedNode);

		if (!oldProduct.equals(selectedProduct)) {
			check(((Category) selectedNode.getData()).getId());
		}

		productService.updateProduct(selectedProduct.getId(), selectedProduct);

		msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				internationalizationService.getMessage(EGenericProduct.SUCCESS
						.getName()),
				internationalizationService
						.getMessage(EGenericProduct.PROD_UPDATED.getName()));
		return msg;
	}

	private Product updateFieldsExceptions(TreeNode selectedNode)
			throws ProductException {
		if (selectedNode == null)
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.NO_CATEG_SEL.getName()));

		if (selectedProduct == null)
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.SEL_PROD_ERR.getName()));

		Product oldProduct = productService
				.findProduct(selectedProduct.getId());
		if (oldProduct == null)
			throw new ProductException(
					internationalizationService
							.getMessage(EGenericProduct.NO_PROD_IN_DB.getName()));
		return oldProduct;
	}

}
