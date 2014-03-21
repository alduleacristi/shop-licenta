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

import org.primefaces.event.FileUploadEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.exception.PhotoException;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.PhotoService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EPhotoProduct;

@URLMappings(mappings = {
		@URLMapping(id = "colorProductSelectPhoto", pattern = "/superadmin/genericProducts/colorProducts/photos/#{colorProductSelectPhotoBean.id}", viewId = "productDescription.xhtml"),
		@URLMapping(id = "colorProductSelectPhotoAdmin", pattern = "/admin/genericProducts/colorProducts/photos/#{colorProductSelectPhotoBean.id}", viewId = "productDescription.xhtml") })
@ManagedBean(name = "colorProductSelectPhotoBean")
@ViewScoped
public class ColorProductSelectPhotoBean implements Serializable {

	private static final long serialVersionUID = -852133938979715046L;

	@EJB
	ColorProductService colorProductService;

	@EJB
	private PhotoService photoService;

	@EJB
	private ConfigurationService confService;

	@EJB
	private InternationalizationService internationalizationService;

	@ManagedProperty(value = "#{id}")
	private long id;

	private ProductColor selectedProduct;
	private List<String> photos;
	private String host;

	@PostConstruct
	private void init() {
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
							"internationalization/superadmin/messages/photoProducts/PhotoProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/photoProducts/PhotoProducts");
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
			displayPhotos();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ProductColor getSelectedProduct() {
		ProductColor product;
		product = (ProductColor) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("selectedProduct");
		if (product == null) {
			product = colorProductService.getProductById(id);
		}
		return product;

	}

	public void setSelectedProduct(ProductColor selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	void displayPhotos() {
		photos = new ArrayList<String>();
		try {
			photos = photoService.displayOfPhotos("id"
					+ selectedProduct.getId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = null;
		try {
			photoService.addPhoto(event, "id" + selectedProduct.getId());
			displayPhotos();
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService
							.getMessage(EPhotoProduct.SUCCESS.getName()),
					internationalizationService
							.getMessage(EPhotoProduct.PHOTO_UPLOADED.getName()));
		} catch (PhotoException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(EPhotoProduct.ERROR
							.getName()), e.getMessage());
		} finally {
			NavigationUtils.addMessage(message);
		}
	}

	public void deletePhotos() {
		try {
			photoService.removePhotos("id" + selectedProduct.getId());
			displayPhotos();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService
							.getMessage(EPhotoProduct.SUCCESS.getName()),
					internationalizationService
							.getMessage(EPhotoProduct.PHOTO_DELETED.getName()));
			NavigationUtils.addMessage(message);
		} catch (PhotoException e) {
			System.out.println(e);
		} finally {

		}
	}

}
