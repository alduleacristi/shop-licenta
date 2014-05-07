package com.siemens.ctbav.intership.shop.internationalization.enums.superadmin;

public enum EManageUsers {
	LIST_A("listA"), USER("username"), DETAIL_A("detailA"), SUCCESS("success"), ERROR(
			"error"), NOT_SEL("notSelected"), USER_ADDED("userAdded"), M1("m1"), M2(
			"m2"), M3("m3"), M4("m4"), PASSWORD("password"), RETYPE("retype"), LIST_O(
			"listO"), DETAIL_O("detailO"), REQUIRED_USER("requiredUser"), REQUIRED_EMAIL(
			"requiredEmail"), REQUIRED_PASSWORD("requiredPassword"), RETYPE_MUST_MATCH(
			"retypeMustMatch"), INVALID_PASSWORD("invalidPassword"), LENGTH(
			"length"), INVALID_EMAIL("invalidEmail"), REQUIRED_CAPTCHA(
			"requiredCaptcha"), INVALID_CAPTCHA("invalidCaptcha"), TYPE("type"), ADRESS_ERROR(
			"adressError"), ACCOUNT_ERROR("accountError");

	private final String name;

	private EManageUsers(final String query) {
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
