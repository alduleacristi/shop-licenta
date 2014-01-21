package com.siemens.ctbav.intership.shop.util;

/**
 * This enum is used to define the user role.
 * 
 * @author cristian.aldulea
 * 
 */
public enum UsersRole {
	SUPER_ADMINISTRATOR("superadmin"), ADMINISTRATOR("admin"), OPERATOR(
			"operator"), CLIENT("client");

	private final String role;

	private UsersRole(final String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return this.role;
	}
}
