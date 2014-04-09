package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum ECart {
	CART("cart"), ARTICLES("articles"), COST("cost"), REMOVE("remove"), PRODUCTS(
			"products"), NO_RECORDS("noRecords"), SEND_COMMAND("sendCommand"), EXIST_ADDRESS(
			"existAddress"), NEW_ADDRESS("newAdress"), ANOTHER_ADDRESS(
			"anotherAddress"), CHOOSE_SIZE("chooseSize"), NR_PRODUCTS(
			"nrProducts"), NOT_ENOUGH_PRODUCTS("notEnoughProducts"), SUCCESS_MESSAGE(
			"successMessage"), REMOVED_FROM_STOCK("removedFromStock"), DESCRIPTION_UNAVAILABLE(
			"descriptionUnavailable"), ONLY("only"), PIECES("pieces"), MODIFY(
			"modify"), PRODUCT("product"), IS_IN_STOCK("isInStock"), ADDRESS_ERROR(
			"addressError"), CHOOSE_COUNTRY("chooseCountry"), CHOOSE_COUNTY(
			"chooseCounty"), CHOOSE_LOCALITY("chooseLocality"), INCORRECT_ADDRESS(
			"incorrectAddress"), PROBLEM("problem"), COMMAND_PROBLEM(
			"commandProblem"), SUCCESS("success"), ERROR("error"),RETURN_COMMAND("returnCommand");

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
