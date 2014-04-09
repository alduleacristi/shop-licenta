package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidProductsException;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EImport;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.importer.ImportService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "importBean")
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "importBean", pattern = "/superadmin/exportProducts/", viewId = "exportProducts.xhtml"),
		@URLMapping(id = "importBeanAdmin", pattern = "/admin/exportProducts/", viewId = "exportProducts.xhtml") })
public class ImportBean {

	@EJB
	private InternationalizationService internationalizationService;

	@EJB
	private ImportService importService;

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

		try {
			importService.importXml(event.getFile().getInputstream());
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_INFO, internationalizationService
							.getMessage(EImport.SUCCESS.getName()),
					internationalizationService
							.getMessage(EImport.SUCCESS_MESSAGE.getName())));
		} catch (InvalidProductsException e) {
			if (e != null) {
				List<InvalidProductException> exceptionList = e.getExceptions();
				for (InvalidProductException exc : exceptionList) {
					StringBuilder msg = new StringBuilder(100);
					msg.append(exc.getMessage()).append("\n");
					List<Throwable> causeList = exc.getCauses();
					for (Throwable c : causeList)
						msg.append("\n").append(c.getMessage());
					FacesMessage msg2 = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, msg.toString(), null);
					NavigationUtils.addMessage(msg2);
				}
			}
		} catch (InvalidFileException e) {
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, internationalizationService
							.getMessage(EImport.ERROR.getName()), e
							.getMessage()));
		} catch (IOException e) {
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, internationalizationService
							.getMessage(EImport.ERROR.getName()), e
							.getMessage()));
		}

	}

	public void uploadCsv(FileUploadEvent event) {
		try {
			importService.importCsv(event.getFile().getInputstream());
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_INFO, internationalizationService
							.getMessage(EImport.SUCCESS.getName()),
					internationalizationService
							.getMessage(EImport.SUCCESS_MESSAGE.getName())));
		} catch (InvalidProductsException e) {
			if (e != null) {
				List<InvalidProductException> exceptionList = e.getExceptions();
				for (InvalidProductException exc : exceptionList) {
					StringBuilder msg = new StringBuilder(100);
					msg.append(exc.getMessage()).append("\n");
					List<Throwable> causeList = exc.getCauses();
					for (Throwable c : causeList)
						msg.append("\n").append(c.getMessage());
					FacesMessage msg2 = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, msg.toString(), null);
					NavigationUtils.addMessage(msg2);
				}
			}
		} catch (InvalidFileException e) {
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, internationalizationService
							.getMessage(EImport.ERROR.getName()), e
							.getMessage()));
		} catch (IOException e) {
			NavigationUtils.addMessage(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, internationalizationService
							.getMessage(EImport.ERROR.getName()), e
							.getMessage()));
		}

	}
}
