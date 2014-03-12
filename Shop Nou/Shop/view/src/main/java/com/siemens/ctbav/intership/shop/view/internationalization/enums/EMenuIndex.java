package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EMenuIndex {
	HOME("home"), LOGOUT("logout");

	private final String name;

	private EMenuIndex(final String query) {
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
