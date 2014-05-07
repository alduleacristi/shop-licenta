package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum ECreateAccount {
	HEADER("panelHeader"), SUCCESS_MESSAGE("succesMessage"), CREATE_ACCOUNT(
			"createAccount"), STEP2_MESSAGE("step2Message"), FIRST_NAME(
			"firstName"), LAST_NAME("lastName"), PHONE_NUMBER("phoneNumber"), FINISH(
			"finish"), ACCIDENTAL("accidentalPage"),ADD_ADRESS("addAdress");
	private final String name;

	private ECreateAccount(final String query) {
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
