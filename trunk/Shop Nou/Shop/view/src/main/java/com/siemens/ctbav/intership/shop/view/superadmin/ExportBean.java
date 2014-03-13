package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorSizeProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.ExportToCsv;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.ExportToXml;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.Exporter;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EExport;

@ManagedBean(name = "exportBean")
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "exportBean", pattern = "/superadmin/exportProducts/", viewId = "exportProducts.xhtml") })
public class ExportBean {

	@EJB
	ColorSizeProductService colorSizeProductService;

	@EJB
	private InternationalizationService internationalizationService;

	private String fileName;
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
			photo = "/resources/exportProducts.jpg";
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/export/Export");
		} else {
			photo = "/resources/exportProductsR.jpg";
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/export/Export");
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPhoto() {
		return photo;
	}

	public void exportToCsv(ActionEvent actionEvent) {
		Exporter exporter = null;
		OutputStream stream = null;
		FacesMessage msg = null;

		exporter = new ExportToCsv();
		try {
			stream = new FileOutputStream(fileName + ".csv");
			export(exporter, stream, msg);
			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void exportToXml(ActionEvent actionEvent) {
		Exporter exporter = null;
		OutputStream stream = null;
		FacesMessage msg = null;

		exporter = new ExportToXml();
		try {
			stream = new FileOutputStream(fileName + ".xml");
			export(exporter, stream, msg);
			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	private void export(Exporter exporter, OutputStream stream, FacesMessage msg) {
		List<ProductColorSize> pcss = colorSizeProductService
				.getAllProductsColorSize();
		if (exporter != null) {
			exporter.export(stream, pcss);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(EExport.SUCCES
							.getName()),
					internationalizationService.getMessage(EExport.SUCCES_M
							.getName()));
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
