package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EPhotoProduct {
	MESSAGE("message"), CATEGORY("category"), DESCRIPTION("description"), SUCCESS(
			"success"), ERROR("error"), COLOR("color"), VIEW("view"), CHOOSE(
			"choose"), REMOVE_PHOTOS("removePhotos"), PHOTO_UPLOADED(
			"photoUploaded"), PHOTO_DELETED("photoDeleted");

	private final String name;

	private EPhotoProduct(final String query) {
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
