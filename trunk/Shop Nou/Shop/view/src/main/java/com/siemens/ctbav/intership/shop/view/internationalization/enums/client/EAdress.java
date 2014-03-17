package com.siemens.ctbav.intership.shop.view.internationalization.enums.client;

public enum EAdress {
	COUNTRY("country"), COUNTY("county"), LOCALITY("locality"), STREET("street"), NUMBER(
			"number"), BLOCK("block"), STAIRCASE("staircase"), FLAT("flat"), MESSAGES(
			"message"), CHOOSE_COUNTRY("chooseCountry"), CHOOSE_COUNTY(
			"chooseCounty"), CHOOSE_LOCALITY("chooseLocality"), NUMBER_REQUIRED(
			"numberRequired");

	private final String name;

	private EAdress(final String query) {
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
