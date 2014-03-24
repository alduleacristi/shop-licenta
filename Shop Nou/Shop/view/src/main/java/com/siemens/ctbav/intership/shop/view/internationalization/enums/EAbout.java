package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EAbout {
	HEADER("header"), FOOTER("footer");
	private final String name;

	private EAbout(final String query) {
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
