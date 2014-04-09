package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EExport;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorSizeProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.ExportToCsv;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.ExportToXml;
import com.siemens.ctbav.intership.shop.service.superadmin.exporter.Exporter;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "exportBean")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "exportBean", pattern = "/superadmin/exportProducts/", viewId = "exportProducts.xhtml"),
		@URLMapping(id = "exportBeanAdmin", pattern = "/admin/exportProducts/", viewId = "exportProducts.xhtml") })
public class ExportBean {

	@EJB
	ColorSizeProductService colorSizeProductService;

	@EJB
	private InternationalizationService internationalizationService;

	private String fileName;
	private String photo;
	private StreamedContent fileXml;
	private StreamedContent fileCsv;
	private InputStream inputStreamXml;
	private InputStream inputStreamCsv;

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

	public StreamedContent getFileXml() {
		fileXml = new DefaultStreamedContent(inputStreamXml, "xml", fileName);
		return fileXml;
	}

	public void setFileXml(StreamedContent fileXml) {
		this.fileXml = fileXml;
	}

	public StreamedContent getFileCsv() {
		fileCsv = new DefaultStreamedContent(inputStreamCsv, "text/csv",
				fileName + ".csv");
		return fileCsv;
	}

	public void setFileCsv(StreamedContent fileCsv) {
		this.fileCsv = fileCsv;
	}

	public void exportToCsv(ActionEvent actionEvent) {
		Exporter exporter = null;
		File stream = null;
		FacesMessage msg = null;

		exporter = new ExportToCsv();
		try {
			stream = File.createTempFile(fileName, ".csv");
		} catch (IOException e1) {
			System.out.println(e1);
		}
		export(exporter, stream, msg);

		try {
			inputStreamCsv = new FileInputStream(stream);
			stream.delete();
		} catch (FileNotFoundException e) {
		}
	}

	public void exportToXml(ActionEvent actionEvent) {
		Exporter exporter = null;
		File stream = null;
		FacesMessage msg = null;

		exporter = new ExportToXml();
		try {
			stream = File.createTempFile(fileName, ".xml");
		} catch (IOException e1) {
			System.out.println(e1);
		}
		export(exporter, stream, msg);

		try {
			inputStreamXml = new FileInputStream(stream);
			stream.delete();
		} catch (FileNotFoundException e) {
		}
	}

	private void export(Exporter exporter, File stream, FacesMessage msg) {
		List<ProductColorSize> pcss = colorSizeProductService
				.getAllProductsColorSize();
		if (exporter != null) {
			try {
				exporter.export(stream, pcss);
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService.getMessage(EExport.SUCCES
								.getName()),
						internationalizationService.getMessage(EExport.SUCCES_M
								.getName()));
			} catch (FileNotFoundException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService.getMessage(EExport.ERROR
								.getName()),
						internationalizationService.getMessage(EExport.ERROR_M
								.getName()));
			}

		}
		NavigationUtils.addMessage(msg);
	}
}
