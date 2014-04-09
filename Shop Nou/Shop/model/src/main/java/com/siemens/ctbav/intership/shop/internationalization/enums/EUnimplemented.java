package com.siemens.ctbav.intership.shop.internationalization.enums;

public enum EUnimplemented {
	SORRY("sorry"), MESSAGE("message");

	private final String name;

	private EUnimplemented(final String query) {
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
