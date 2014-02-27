package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.jcr.RepositoryException;

import org.primefaces.event.FileUploadEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.PhotoService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@URLMappings(mappings = { @URLMapping(id = "colorProductSelectPhoto", pattern = "/superadmin/genericProducts/colorProducts/photos/#{colorProductSelectPhotoBean.id}", viewId = "productDescription.xhtml") })
@ManagedBean(name = "colorProductSelectPhotoBean")
@ViewScoped
public class ColorProductSelectPhotoBean implements Serializable {

	private static final long serialVersionUID = -852133938979715046L;

	@EJB
	ColorProductService colorProductService;

	@EJB
	private PhotoService photoService;

	@ManagedProperty(value = "#{id}")
	private long id;

	private ProductColor selectedProduct;
	private List<String> photos;

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
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
					"Photo successfully uploaded");
		} catch (RepositoryException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
		} catch (IOException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
		} finally {
			NavigationUtils.addMessage(message);
		}
	}

	public void deletePhotos() {
		try {
			photoService.removePhotos("id" + selectedProduct.getId());
			displayPhotos();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Success", "Photos successfully deleted");
			NavigationUtils.addMessage(message);
		} catch (RepositoryException e) {
			System.out.println(e);
		}
	}

}
