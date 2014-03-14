package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum EMenuLeft {
	MENU("menu"),
	MANAGE_CATEGS("managementCategories"),
	MANAGE_SIZES("managementSizes"),
	MANAGE_COLORS("managementColors"),
	MANAGE_PROD("managementProducts"),
	UPDATE_STOCK("updateStock"),
	EXP_PROD("exportProducts"),
	IMP_PROD("importProducts"),
	MANAGE_ADMINS("managementAdmins"),
	MANAGE_NEWSL("managementNewsL"),
	MANAGE_SALES("managementSales"),
	MANAGE_OPERATORS("managementOperators"),
	RETURNED_COMMANDS("returnedCommands"),
	VIEW_REPORTS("viewReports"),
	CHAT_WITH_CLIENTS("chatWithClients"),
	VIEW_COMMANDS("viewCommands"),
	VIEW_CLIENTS("viewClients"),
	EDIT_PROFILE("editProfile");

	private final String name;

	private EMenuLeft(final String query) {
		this.name = query;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
