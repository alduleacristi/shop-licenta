package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum ECategory {
	TEMPLATE1("template1"), TEMPLATE2("template2"), CATEGORYDELETED(
			"categoryDeleted"), CREATEPARENTEXCEPTION("createParentException"), CATEGORYADDED(
			"categoryAdded"), NOCATEGORYSELECTED("noCategorySelected"), CATEGORYSNAMEUPDATED(
			"categorysNameUpdates"), CATEGORYSNAMEANDPARENTUPDATED(
			"categorysNameAndParentUpdated "), CATEGORYSPARENTUPDATED(
			"categorysParentUpdated "), DONTDELETEROOT("dontDeleteRoot"), NOTHINGTOUPDATE(
			"nothingToUpdate"), CREATEM("createM"), UPDATEM("updateM"), LENGTH(
			"length"), ERRORPARENT("errorParent"), CYRCULARD(
			"cyrcularDependency"), SELECTPARENT("selectParent"), INVALIDNAME1(
			"invalidName1"), INVALIDNAME2("invalidName2"), SUCCESS("success"), ERROR(
			"error");

	private final String name;

	private ECategory(final String query) {
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
