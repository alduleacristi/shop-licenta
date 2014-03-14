package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EColorSizeProducts;

@ManagedBean(name = "internationalizationColorSizeProducts")
@RequestScoped
public class InternationalizationColorSizeProducts implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	private String message;
	private String category;
	private String description;
	private String color;
	private String view;
	private String sizes;
	private String available;
	private String choose;
	private String avaliableS;
	private String nrPieces;
	private String add;
	private String addPieces;
	private String setPieces;

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
							"internationalization/superadmin/messages/colorSizeProducts/ColorSizeProducts");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/colorSizeProducts/ColorSizeProducts");
		}
	}

	public String getMessage() {
		message = internationalizationService
				.getMessage(EColorSizeProducts.MESSAGE.getName());
		return message;
	}

	public String getCategory() {
		category = internationalizationService
				.getMessage(EColorSizeProducts.CATEGORY.getName());
		return category;
	}

	public String getDescription() {
		description = internationalizationService
				.getMessage(EColorSizeProducts.DESCRIPTION.getName());
		return description;
	}

	public String getColor() {
		color = internationalizationService.getMessage(EColorSizeProducts.COLOR
				.getName());
		return color;
	}

	public String getView() {
		view = internationalizationService.getMessage(EColorSizeProducts.VIEW
				.getName());
		return view;
	}

	public String getSizes() {
		sizes = internationalizationService.getMessage(EColorSizeProducts.SIZES
				.getName());
		return sizes;
	}

	public String getAvailable() {
		available = internationalizationService
				.getMessage(EColorSizeProducts.AVAILABLE.getName());
		return available;
	}

	public String getChoose() {
		choose = internationalizationService
				.getMessage(EColorSizeProducts.CHOOSE.getName());
		return choose;
	}

	public String getAvaliableS() {
		avaliableS = internationalizationService
				.getMessage(EColorSizeProducts.AVAILABLE_SIZES.getName());
		return avaliableS;
	}

	public String getNrPieces() {
		nrPieces = internationalizationService
				.getMessage(EColorSizeProducts.NR_PIECES.getName());
		return nrPieces;
	}

	public String getAdd() {
		add = internationalizationService.getMessage(EColorSizeProducts.ADD
				.getName());
		return add;
	}

	public String getAddPieces() {
		addPieces = internationalizationService
				.getMessage(EColorSizeProducts.ADD_PIECES.getName());
		return addPieces;
	}

	public String getSetPieces() {
		setPieces = internationalizationService
				.getMessage(EColorSizeProducts.SET_PIECES.getName());
		return setPieces;
	}

}
