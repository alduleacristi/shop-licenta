package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum EPersonalData {
	FIRST_NAME("firstName"), LAST_NAME("lastName"), PHONE("phone"), SUCCESS(
			"success"), ERROR("error"), SUCCESS_MESSAGE("successMessage"), ERROR_MESSAGE(
			"errorMessage");

	private final String name;

	private EPersonalData(final String query) {
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
