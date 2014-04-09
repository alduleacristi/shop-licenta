package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.ESize;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "internationalizationSize")
@RequestScoped
public class InternationalizationSize implements Serializable {

	private static final long serialVersionUID = -2490626256811276662L;

	@EJB
	private InternationalizationService internationalizationService;

	private String message1;
	private String message2;
	private String message3;
	private String message4;
	private String category;
	private String size;
	private String table1;
	private String table2;
	private String sizeDetail;
	private String sizeName;

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
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/sizes/Sizes");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/sizes/Sizes");
		}
	}

	public String getMessage1() {
		message1 = internationalizationService.getMessage(ESize.M1.getName());
		return message1;
	}

	public String getMessage2() {
		message2 = internationalizationService.getMessage(ESize.M2.getName());
		return message2;
	}

	public String getMessage3() {
		message3 = internationalizationService.getMessage(ESize.M3.getName());
		return message3;
	}

	public String getMessage4() {
		message4 = internationalizationService.getMessage(ESize.M4.getName());
		return message4;
	}

	public String getCategory() {
		category = internationalizationService.getMessage(ESize.CATEGORY
				.getName());
		return category;
	}

	public String getSize() {
		size = internationalizationService.getMessage(ESize.SIZE.getName());
		return size;
	}

	public String getTable1() {
		table1 = internationalizationService.getMessage(ESize.T1.getName());
		return table1;
	}

	public String getTable2() {
		table2 = internationalizationService.getMessage(ESize.T2.getName());
		return table2;
	}

	public String getSizeDetail() {
		sizeDetail = internationalizationService.getMessage(ESize.SIZE_DETAIL
				.getName());
		return sizeDetail;
	}

	public String getSizeName() {
		sizeName = internationalizationService.getMessage(ESize.SIZE_NAME
				.getName());
		return sizeName;
	}
}
