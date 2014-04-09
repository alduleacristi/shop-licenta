package com.siemens.ctbav.intership.shop.internationalization.enums;

public enum ECoin {
	COIN("coin"), UPDATE("update"), ADD("add");
	private final String name;

	private ECoin(final String query) {
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
