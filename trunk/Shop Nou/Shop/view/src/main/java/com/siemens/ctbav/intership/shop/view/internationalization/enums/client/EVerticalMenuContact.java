package com.siemens.ctbav.intership.shop.view.internationalization.enums.client;

public enum EVerticalMenuContact {
	OPTIONS("options"), SEND_MESSAGE("sendMessage"), MESSAGE_HISTORY(
			"messageHistory");

	private final String name;

	private EVerticalMenuContact(final String query) {
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
