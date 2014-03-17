package com.siemens.ctbav.intership.shop.view.internationalization.enums;

public enum EAdvertise {
	ADEVRTISE1("advertise1"), SITE1("site1"), TITLE1("title1"), ADEVRTISE2(
			"advertise2"), SITE2("site2"), TITLE2("title2"), TITLE3("title3"), SITE3(
			"site3"), ADEVRTISE3("advertise3"), TITLE4("title4"), SITE4("site4"), ADEVRTISE4(
			"advertise4");

	private final String name;

	private EAdvertise(final String query) {
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