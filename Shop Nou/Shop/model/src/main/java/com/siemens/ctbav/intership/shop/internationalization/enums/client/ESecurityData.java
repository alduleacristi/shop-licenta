package com.siemens.ctbav.intership.shop.internationalization.enums.client;

public enum ESecurityData {
	OLD_PASSWORD("oldPass"), PASSWORD("pass"), RETYPE("retype"), SUCCESS(
			"success"), ERROR("error"), OLD_PASS_INCORRECT("oldPassIncorrect"), NOT_THE_SAME(
			"notTheSame"), SUCCESS_MESSAGE("successMessage"), INDISPONIBILITY(
			"indisponibility"), PASSWORD_MESSAGE("passwordMessage"), FIRST_NAME_MESSAGE(
			"firstNameMessage"), LAST_NAME_MESSAGE("lastNameMessage"), PHONE_MESSAGE(
			"phoneMessage");

	private final String name;

	private ESecurityData(final String query) {
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
