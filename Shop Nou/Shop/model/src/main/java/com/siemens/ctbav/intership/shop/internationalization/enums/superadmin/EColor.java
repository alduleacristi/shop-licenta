package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EColor {
	COLORS("colors"), COLOR_DETAIL("colorDetail"), COLOR_NAME("colorName"), COLOR_DESCRIPTION(
			"colorDescription"), EDIT_COLOR("editTheColor"), SUCCESS("success"), ERROR(
			"error"), COLOR_EDITED("colorEdited"), COLOR_ADDED("colorAdded"), COLOR_DELETED(
			"colorDeleted"), CHOSE("chose");

	private final String name;

	private EColor(final String query) {
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
