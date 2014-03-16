package com.siemens.ctbav.intership.shop.view.internationalization.enums.client;

public enum ECart {
	CART("cart"), ARTICLES("articles"), COST("cost"), REMOVE("remove"), PRODUCTS(
			"products"), NO_RECORDS("noRecords"), SEND_COMMAND("sendCommand"), EXIST_ADDRESS(
			"existAddress"), NEW_ADDRESS("newAdress"), ANOTHER_ADDRESS(
			"anotherAddress");

	private final String name;

	private ECart(final String query) {
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
