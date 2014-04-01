package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum EImport {
	XML("xml"), CSV("csv"), MESSAGE("message"), IMPORT("import"), SUCCESS(
			"success"), SUCCESS_MESSAGE("successMessage"), ERROR("error"), ERROR_MESSAGE(
			"errorMessage");
	private final String name;

	private EImport(final String query) {
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
