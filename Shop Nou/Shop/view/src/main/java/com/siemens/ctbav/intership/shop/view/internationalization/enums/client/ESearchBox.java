package com.siemens.ctbav.intership.shop.view.internationalization.enums.client;

public enum ESearchBox {
	SEARCH_BY("searchBy"), KEYWORD("keyword"), PRICE("price"), SEARCH("search"), SUPPORT(
			"support"), CATEGORIES("categories");

	private final String name;

	private ESearchBox(final String query) {
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