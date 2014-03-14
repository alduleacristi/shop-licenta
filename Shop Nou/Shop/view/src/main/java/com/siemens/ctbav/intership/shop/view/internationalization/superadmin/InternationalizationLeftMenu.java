package com.siemens.ctbav.intership.shop.view.internationalization.superadmin;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EMenuLeft;

@ManagedBean(name = "internationalizationLeftMenuBean")
@RequestScoped
public class InternationalizationLeftMenu {

	@EJB
	private InternationalizationService internationalizationService;

	private boolean isEnglishSelected;
	private String menu;
	private String managementCategories;
	private String managementSizes;
	private String managementColors;
	private String managementProducts;
	private String updateStock;
	private String exportProducts;
	private String importProducts;
	private String managementAdmins;
	private String managementNewsL;
	private String managementSales;
	private String managementOperators;
	private String returnedCommands;
	private String viewReports;
	private String chatWithClients;
	private String viewCommands;
	private String viewClients;
	private String editProfile;

	@PostConstruct
	private void init() {
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
					"internationalization/superadmin/menu/menuLeft/MenuLeft");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/menu/menuLeft/MenuLeft");
		}
	}

	public boolean isEnglishSelected() {
		return isEnglishSelected;
	}

	public String getMenu() {
		menu = internationalizationService.getMessage(EMenuLeft.MENU.getName());
		return menu;
	}

	public String getManagementCategories() {
		managementCategories = internationalizationService
				.getMessage(EMenuLeft.MANAGE_CATEGS.getName());
		return managementCategories;
	}

	public String getManagementSizes() {
		managementSizes = internationalizationService
				.getMessage(EMenuLeft.MANAGE_SIZES.getName());
		return managementSizes;
	}

	public String getManagementColors() {
		managementColors = internationalizationService
				.getMessage(EMenuLeft.MANAGE_COLORS.getName());
		return managementColors;
	}

	public String getManagementProducts() {
		managementProducts = internationalizationService
				.getMessage(EMenuLeft.MANAGE_PROD.getName());
		return managementProducts;
	}

	public String getUpdateStock() {
		updateStock = internationalizationService
				.getMessage(EMenuLeft.UPDATE_STOCK.getName());
		return updateStock;
	}

	public String getExportProducts() {
		exportProducts = internationalizationService
				.getMessage(EMenuLeft.EXP_PROD.getName());
		return exportProducts;
	}

	public String getImportProducts() {
		importProducts = internationalizationService
				.getMessage(EMenuLeft.IMP_PROD.getName());
		return importProducts;
	}

	public String getManagementAdmins() {
		managementAdmins = internationalizationService
				.getMessage(EMenuLeft.MANAGE_ADMINS.getName());
		return managementAdmins;
	}

	public String getManagementNewsL() {
		managementNewsL = internationalizationService
				.getMessage(EMenuLeft.MANAGE_NEWSL.getName());
		return managementNewsL;
	}

	public String getManagementSales() {
		managementSales = internationalizationService
				.getMessage(EMenuLeft.MANAGE_SALES.getName());
		return managementSales;
	}

	public String getManagementOperators() {
		managementOperators = internationalizationService
				.getMessage(EMenuLeft.MANAGE_OPERATORS.getName());
		return managementOperators;
	}

	public String getReturnedCommands() {
		returnedCommands = internationalizationService
				.getMessage(EMenuLeft.RETURNED_COMMANDS.getName());
		return returnedCommands;
	}

	public String getViewReports() {
		viewReports = internationalizationService
				.getMessage(EMenuLeft.VIEW_REPORTS.getName());
		return viewReports;
	}

	public String getChatWithClients() {
		chatWithClients = internationalizationService
				.getMessage(EMenuLeft.CHAT_WITH_CLIENTS.getName());
		return chatWithClients;
	}

	public String getViewCommands() {
		viewCommands = internationalizationService
				.getMessage(EMenuLeft.VIEW_COMMANDS.getName());
		return viewCommands;
	}

	public String getViewClients() {
		viewClients = internationalizationService
				.getMessage(EMenuLeft.VIEW_CLIENTS.getName());
		return viewClients;
	}

	public String getEditProfile() {
		editProfile = internationalizationService
				.getMessage(EMenuLeft.EDIT_PROFILE.getName());
		return editProfile;
	}

}
