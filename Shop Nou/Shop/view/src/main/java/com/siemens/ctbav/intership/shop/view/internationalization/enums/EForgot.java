package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EForgot {
	FORGOT("forgot"), ENTER_EMAIL("enterEmail"), EMAIL("email"), CHANGE_PASSWORD(
			"changePassword"), PASSWORD("password"), RETYPE("retype");

	private final String name;

	private EForgot(final String query) {
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
