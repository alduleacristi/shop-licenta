package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EPhotoProduct;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationPhotoProducts")
@RequestScoped
public class InternationalizationPhotoProducts implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String message;
	private String category;
	private String description;
	private String color;
	private String view;
	private String choose;
	private String remove;

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

	public String getMessage() {
		message = internationalizationService.getMessage(EPhotoProduct.MESSAGE
				.getName());
		return message;
	}

	public String getCategory() {
		category = internationalizationService
				.getMessage(EPhotoProduct.CATEGORY.getName());
		return category;
	}

	public String getDescription() {
		description = internationalizationService
				.getMessage(EPhotoProduct.DESCRIPTION.getName());
		return description;
	}

	public String getColor() {
		color = internationalizationService.getMessage(EPhotoProduct.COLOR
				.getName());
		return color;
	}

	public String getView() {
		view = internationalizationService.getMessage(EPhotoProduct.VIEW
				.getName());
		return view;
	}

	public String getChoose() {
		choose = internationalizationService.getMessage(EPhotoProduct.CHOOSE
				.getName());
		return choose;
	}

	public String getRemove() {
		remove = internationalizationService
				.getMessage(EPhotoProduct.REMOVE_PHOTOS.getName());
		return remove;
	}

}
