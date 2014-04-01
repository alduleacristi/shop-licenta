package com.siemens.ctbav.intership.shop.view.superadmin;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "importBean")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "importBean", pattern = "/superadmin/exportProducts/", viewId = "exportProducts.xhtml"),
		@URLMapping(id = "importBeanAdmin", pattern = "/admin/exportProducts/", viewId = "exportProducts.xhtml") })
public class ImportBean {

	@EJB
	private InternationalizationService internationalizationService;

	private String photo;

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
			photo = "/resources/importProducts.jpg";
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/export/Export");
		} else {
			photo = "/resources/importProductsR.jpg";
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/export/Export");
		}
	}

	public String getPhoto() {
		return photo;
	}

	public void uploadXml(FileUploadEvent event) {

	}

	public void uploadCsv(FileUploadEvent event) {

	}
}
