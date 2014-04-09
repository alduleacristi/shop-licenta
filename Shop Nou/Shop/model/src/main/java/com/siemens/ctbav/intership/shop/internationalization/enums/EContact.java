package com.siemens.ctbav.intership.shop.internationalization.enums;

public enum EContact {
	SEND("send"), MESSAGE("message"), SUBJECT("subject"), INFO("info"), REQUIRED_SUBJECT(
			"requiredSubject"), REQUIRED_MESSAGE("requiredMessage");

	private final String name;

	private EContact(final String query) {
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
