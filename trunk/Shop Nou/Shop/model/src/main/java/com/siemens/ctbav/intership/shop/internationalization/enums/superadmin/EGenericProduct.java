package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EGenericProduct {
	MESSAGE("message"), CATEGORY("category"), DESCRIPTION("description"), PRICE(
			"price"), SALE("sale"), PROD_DETAIL("productDetail"), PROD_NAME(
			"productName"), PROD_CATEG("productCategory"), CHANGE_PARENT(
			"changeParent"), PROD_PRICE("productPrice"), PROD_DESCRIPTION(
			"productDescription"), SUCCESS("success"), ERROR("error"), SEL_PROD_ERR(
			"selectProductError"), PROD_DELETED("productDeleted"), PROD_ADDED(
			"productAdded"), PROD_UPDATED("productUpdated"), NO_CATEG_SEL(
			"noCategSelected"), DUPLICATE_PROD("duplicateProduct"), CATEG_CHANGED(
			"categoryChanged"), NO_PARENT_SEL("noParentSelected"), NO_PROD_IN_DB(
			"noProductInTheDB"), NAME_IS_REQUIRED("nameIsRequired"), PRICE_IS_REQUIRED(
			"priceIsRequired"), LENGHT("length"), PRICE_RESTRICTION(
			"priceRestriction"), SALE_REQUIRED("saleRequired"), REINDEX_BUTTON(
			"reindexProducts"), REINDEX_TOOLTIP("reindexTooltip");

	private final String name;

	private EGenericProduct(final String query) {
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
