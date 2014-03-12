package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EGeneric {
	CREATE("create"), DELETE("delete"), UPDATE("update"), YES("yes"), NO("no"), NAME(
			"name"), CONFIRMATION("confirmation"), CONFIRM_MESSAGE(
			"confirmMessage"), CANCEL("cancel");

	private final String name;

	private EGeneric(final String query) {
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
