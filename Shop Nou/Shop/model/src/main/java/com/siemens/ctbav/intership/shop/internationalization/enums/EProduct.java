package com.siemens.ctbav.intership.shop.internationalization.enums;

public enum EProduct {
	PRODUCT_NAME("productName"), PRICE("price"), OUT_OF_STOCK("outOfStock"), IN_STOCK(
			"inStock"), PROD_INFO("productInfo"), NAME("name"), CATEGORY(
			"category"), DESCRIPTION("description"), COLOR("color"), CHOOSE_COLOR(
			"chooseColor"), CHOOSE_SIZE("chooseSize"), NR_PIECES("nrPieces"), ADD(
			"add"), CHANGE_QTY("changeQty"), ITEMS_NOW("itemsNow"), SIZE("size"), RECOMMAND(
			"recommad"), SALE("sale");

	private final String name;

	private EProduct(final String query) {
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
