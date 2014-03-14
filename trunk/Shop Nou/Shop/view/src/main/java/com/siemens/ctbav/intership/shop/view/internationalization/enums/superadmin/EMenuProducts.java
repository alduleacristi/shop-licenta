package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum EMenuProducts {
	MANAGE_GENERIC("managementGeneric"), MANAGE_COLORS("manageColors"), MANAGE_PHOTOS(
			"managePhotos"), MANAGE_SIZES("manageSizes");

	private final String name;

	private EMenuProducts(final String query) {
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
