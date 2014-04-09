package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum EMenuBox {
	HOME("home"), CREATE("create"), CONTACT("contact"), STORE("store"), LOGIN(
			"login"), LOGOUT("logout"), ACCOUNT("account");

	private final String name;

	private EMenuBox(final String query) {
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
