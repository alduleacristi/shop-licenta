package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum EClientMessages {
	HI("hi");

	private final String name;

	private EClientMessages(final String query) {
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
