package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum ESize {
	M1("message1"), M2("message2"), M3("message3"), M4("message4"), CATEGORY(
			"category"), SIZE("size"), T1("table1"), T2("table2"), SIZE_DETAIL(
			"sizeDetail"), SIZE_NAME("sizeName"), SUCCESS("success"), ERROR(
			"error"), SIZE_EX_SELECT("sizeExceptionSelect"), SIZE_EX_UNIQUE(
			"sizeExceptionUnique"), SIZE_EX_SAME("sizeExceptionTheSame"), CATEG_EX_SELECT(
			"categoryExceptionSelect"), SIZE_DELETED("sizeDeleted"), SIZE_ADDED(
			"sizeAdded"), SIZE_EDITED("sizeEdited"), LENGTH("length"), NAME_NO_SET(
			"nameNotSet");

	private final String name;

	private ESize(final String query) {
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