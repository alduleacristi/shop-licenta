package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorSizeProductException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.model.Size;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorSizeProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.SizeService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EColorSizeProducts;

@URLMappings(mappings = {
		@URLMapping(id = "colorProductSizeSelect", pattern = "/superadmin/genericProducts/colorProducts/sizes/#{colorProductSizeSelectBean.id}", viewId = "productDescription.xhtml"),
		@URLMapping(id = "colorProductSizeSelectUpdate", pattern = "/superadmin/updateStoc/#{colorProductSizeSelectBean.id}", viewId = "productDescription.xhtml") })
@ManagedBean(name = "colorProductSizeSelectBean")
@ViewScoped
public class ColorProductSizeSelectBean implements Serializable {

	private static final long serialVersionUID = -852133938979715046L;

	@EJB
	ColorProductService colorProductService;

	@EJB
	private SizeService sizeService;

	@EJB
	private ColorSizeProductService colorSizeProductService;

	@EJB
	private ConfigurationService confService;

	@EJB
	private InternationalizationService internationalizationService;

	@ManagedProperty(value = "#{id}")
	private long id;

	private ProductColor selectedProduct;
	private ProductColorSize pcs;
	private Long idSelectedProductColorSize;
	private Long idSelectedSize;
	private String nrPieces, host;
	private List<Size> allSizes;
	private boolean selectedSize;

	@PostConstruct
	void initialization() {
		this.host = confService.getHost();
		initSizes();
		internationalizationInit();
	}

	void initSizes() {
		idSelectedProductColorSize = (long) -1;
		TreeNode selectedNode = (TreeNode) (FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedNode"));
		Category selectedCategory = (Category) (selectedNode.getData());
		if (selectedCategory != null) {
			long idSelectedcategory = selectedCategory.getId();
			allSizes = new ArrayList<Size>();
			if (idSelectedcategory > 0) {
				List<Size> currentSizes = sizeService
						.getSizesByCategory(idSelectedcategory);
				List<Size> parentsSizes = sizeService
						.storedProcedureGetParentsSizes(idSelectedcategory);
				allSizes.addAll(currentSizes);
				allSizes.addAll(parentsSizes);
			}
		}
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
							"internationalization/superadmin/messages/colorSizeProducts/ColorSizeProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/colorSizeProducts/ColorSizeProducts");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		if (id != 0) {
			selectedProduct = colorProductService.getProductById(id);
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("selectedProduct", selectedProduct);
		}
	}

	public ProductColor getSelectedProduct() {
		ProductColor product;
		product = (ProductColor) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedProduct");
		if (product == null) {
			product = colorProductService.getProductById(id);
		}
		setFirstSize();
		return product;

	}

	public void setSelectedProduct(ProductColor selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public ProductColorSize getPcs() {
		return pcs;
	}

	public void setPcs(ProductColorSize pcs) {
		this.pcs = pcs;
	}

	public Long getIdSelectedProductColorSize() {
		return idSelectedProductColorSize;
	}

	public void setIdSelectedProductColorSize(Long idSelectedProductColorSize) {
		setSelectedSize(true);
		pcs = colorSizeProductService
				.getProductById(idSelectedProductColorSize);
		this.idSelectedProductColorSize = idSelectedProductColorSize;
	}

	public Long getIdSelectedSize() {
		return idSelectedSize;
	}

	public void setIdSelectedSize(Long idSelectedSize) {
		this.idSelectedSize = idSelectedSize;
	}

	public String getNrPieces() {
		return nrPieces;
	}

	public void setNrPieces(String nrPieces) {
		this.nrPieces = nrPieces;
	}

	public List<Size> getAllSizes() {
		return allSizes;
	}

	public void setAllSizes(List<Size> allSizes) {
		this.allSizes = allSizes;
	}

	public boolean isSelectedSize() {
		return selectedSize;
	}

	public void setSelectedSize(boolean selectedSize) {
		this.selectedSize = selectedSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void add(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		FacesMessage msg = null;
		Long idProductColorSize = alreadyExists();
		Long nr = Long.parseLong(nrPieces);
		try {
			if (nr <= 0)
				throw new Exception("Invalid number");
			if (idProductColorSize > 0) {
				colorSizeProductService.addPieces(idProductColorSize, nr);
				create = true;
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService
								.getMessage(EColorSizeProducts.SUCCESS
										.getName()),
						internationalizationService
								.getMessage(EColorSizeProducts.PIECES_ADDED
										.getName()));
			} else {
				colorSizeProductService.addProductColorSize(
						selectedProduct.getId(), nr, idSelectedSize);
				selectedProduct = colorProductService
						.getProductById(selectedProduct.getId());
				create = true;
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService
								.getMessage(EColorSizeProducts.SUCCESS
										.getName()),
						internationalizationService
								.getMessage(EColorSizeProducts.SIZE_ADDED
										.getName()));
			}
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EColorSizeProducts.ERROR.getName()),
					e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private Long alreadyExists() {
		List<ProductColorSize> pcss = selectedProduct.getProductColorSize();
		for (ProductColorSize pcs : pcss) {
			if (idSelectedSize == pcs.getSize().getId()) {
				return pcs.getId();
			}
		}
		return (long) -1;
	}

	public void delete(ActionEvent actionEvent) {
		FacesMessage msg = null;
		try {
			if (idSelectedProductColorSize == -1) {
				throw new ColorSizeProductException(
						internationalizationService
								.getMessage(EColorSizeProducts.SELECT_SIZE_ERR
										.getName()));
			}
			colorSizeProductService
					.removeProductColorSize(idSelectedProductColorSize);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService
							.getMessage(EColorSizeProducts.SUCCESS.getName()),
					internationalizationService
							.getMessage(EColorSizeProducts.SIZE_DELETED
									.getName()));
		} catch (ColorSizeProductException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EColorSizeProducts.ERROR.getName()),
					e.getMessage());
		} finally {
			NavigationUtils.addMessage(msg);
			NavigationUtils
					.redirect("/Shop4j/superadmin/genericProducts/colorProducts/sizes/"
							+ selectedProduct.getId());
		}
	}

	public Long getAvailablePieces() {
		setFirstSize();
		ProductColorSize pcs = colorSizeProductService
				.getProductById(idSelectedProductColorSize);
		if (pcs != null) {
			return pcs.getNrOfPieces();
		}
		return (long) 0;
	}

	private void setFirstSize() {
		if (idSelectedProductColorSize == -1) {
			if (selectedProduct.getProductColorSize().size() > 0) {
				idSelectedProductColorSize = selectedProduct
						.getProductColorSize().get(0).getId();
				pcs = colorSizeProductService
						.getProductById(idSelectedProductColorSize);
			}
		}
	}

	public void edit(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean edit = false;
		FacesMessage msg = null;
		Long nr = pcs.getNrOfPieces();
		try {
			if (nr <= 0)
				throw new Exception(
						internationalizationService
								.getMessage(EColorSizeProducts.INVALID
										.getName()));
			colorSizeProductService.editPieces(pcs.getId(), nr);
			edit = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService
							.getMessage(EColorSizeProducts.SUCCESS.getName()),
					internationalizationService
							.getMessage(EColorSizeProducts.PIECES_EDITED
									.getName()));
		} catch (Exception e) {
			edit = false;
			msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EColorSizeProducts.ERROR.getName()),
					internationalizationService
							.getMessage(EColorSizeProducts.ERROR_EDIT.getName()));
			edit = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("edit", edit);
		}
	}

	public void addPieces(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean add = false;
		FacesMessage msg = null;
		Long nr = Long.parseLong(nrPieces);
		try {
			if (nr <= 0)
				throw new Exception("Invalid number");
			if (idSelectedProductColorSize > 0) {
				colorSizeProductService.addPieces(idSelectedProductColorSize,
						nr);
				add = true;
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService
								.getMessage(EColorSizeProducts.SUCCESS
										.getName()),
						internationalizationService
								.getMessage(EColorSizeProducts.PIECES_ADDED
										.getName()));
			} else {
				throw new ColorSizeProductException(
						internationalizationService
								.getMessage(EColorSizeProducts.SELECT_SIZE_ERR
										.getName()));
			}
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService
							.getMessage(EColorSizeProducts.ERROR.getName()),
					e.getMessage());
			add = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("add", add);
		}
	}
}
