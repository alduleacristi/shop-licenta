package com.siemens.ctbav.intership.shop.internationalization.enums;

public enum EErrorPage {
	ERROR_MESSAGE_PAGE_NOT_FOUND("errorMessage"),ACCESS_DENIED("message");

	private final String name;

	private EErrorPage(final String query) {
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
