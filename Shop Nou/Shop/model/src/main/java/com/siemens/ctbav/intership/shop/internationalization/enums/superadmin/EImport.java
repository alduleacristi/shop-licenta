package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EImport {
	XML("xml"), CSV("csv"), MESSAGE("message"), IMPORT("import"), SUCCESS(
			"success"), SUCCESS_MESSAGE("successMessage"), ERROR("error"), ERROR_MESSAGE(
			"errorMessage"), CATEGORY_NOT_A_NUMBER("categoryNotANumber"), CATEGORY_DOES_NOT_EXISTS(
			"categoryDoesNotExist"), SIZE_IS_NOT_A_NUMBER("sizeIsNotANumber"), SIZE_DOES_NOT_EXIST(
			"sizeDoesNotExist"), COLOR_IS_NOT_A_NUMBER("colorIsNotANumber"), COLOR_DOES_NOT_EXISTS(
			"colorDoesNotExist"), PRICE_IS_NOT_A_NUMBER("priceIsNotANumber"), PRICE_MUST_BE_GRATER(
			"priceMustBeGrater"), NR_PCS_IS_NOT_A_NUMBER("nrPcsIsNotANumber"), NR_PCS_MUST_BE_GRATER(
			"nrPcsMustBeGrater"), EXCEPTION_AT_PRODUCT("exceptionAtProduct"), REDUCTION_NOT_A_NUMBER(
			"reductionNotANumber"), REDUCTION_REQUIREMENT("reductionRequired");
	private final String name;

	private EImport(final String query) {
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
