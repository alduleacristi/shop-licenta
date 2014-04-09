package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EExport {
	XML("xml"), CSV("csv"), MESSAGE("message"), EXPORT("export"), FILE(
			"filename"), SUCCES("success"), SUCCES_M("successMessage"), REQUIRED(
			"required"), ERROR("error"), ERROR_M("errorMessage");

	private final String name;

	private EExport(final String query) {
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
