package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum ELogin {
	LOGIN("login"), USERNAME("username"), PASSWORD("password"), FORGOT("forgot");

	private final String name;

	private ELogin(final String query) {
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
