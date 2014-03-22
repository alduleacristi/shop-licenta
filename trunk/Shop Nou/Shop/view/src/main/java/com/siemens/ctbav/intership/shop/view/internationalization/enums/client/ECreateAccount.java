package com.siemens.ctbav.intership.shop.view.internationalization.enums.client;

public enum ECreateAccount {
	HEADER("panelHeader"),SUCCESS_MESSAGE("succesMessage");
	
	private final String name;

	private ECreateAccount(final String query) {
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
