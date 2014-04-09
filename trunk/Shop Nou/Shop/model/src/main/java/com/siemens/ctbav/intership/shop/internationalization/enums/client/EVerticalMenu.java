package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum EVerticalMenu {
	OPTIONS("options"), PERSONAL_DATA("personalData"), SECURITY_DATA(
			"securityData"), VIEW_HISTORY("viewHistory");

	private final String name;

	private EVerticalMenu(final String query) {
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