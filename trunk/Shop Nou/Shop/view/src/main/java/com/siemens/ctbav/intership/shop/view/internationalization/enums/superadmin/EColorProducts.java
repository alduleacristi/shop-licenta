package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum EColorProducts {
	MESSAGE("message"), CATEGORY("category"), DESCRIPTION("description"), PRICE(
			"price"), COLORS("colors"), PROD_DETAIL("productDetail"), PROD_NAME(
			"productName"), PROD_CATEG("productCategory"), SELECT_COLOR(
			"selectColor"), PROD_PRICE("productPrice"), PROD_DESCRIPTION(
			"productDescription"), SUCCESS("success"), ERROR("error"), COLOR_ADDED(
			"colorAdded"), COLOR_DELETED("colorDeleted"), COLOR_UPDATED(
			"colorUpdated"), NO_PROD_OR_COL_SEL("noProdOrColorSelected"), COLOR_EXISTS(
			"colorExists"), NO_NEW_COL_SEL("noNewColorSel");

	private final String name;

	private EColorProducts(final String query) {
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
