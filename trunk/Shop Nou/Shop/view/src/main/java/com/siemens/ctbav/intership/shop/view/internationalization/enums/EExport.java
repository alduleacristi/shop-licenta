package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EExport {
	XML("xml"), CSV("csv"), MESSAGE("message"), EXPORT("export"), FILE(
			"filename"), SUCCES("success"), SUCCES_M("successMessage"), REQUIRED(
			"required");

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
