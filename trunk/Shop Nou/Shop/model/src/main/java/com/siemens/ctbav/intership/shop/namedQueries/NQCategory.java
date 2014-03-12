package com.siemens.ctbav.intership.shop.namedQueries;

public enum NQCategory {
	GET_ALL_CATEGORIES("getAllCategories"), GET_PARENTS_CATEGORY(
			"callParentsCategory"), GET_CHILDREN_CATEGORY(
			"callChildrenCategory");

	private final String query;

	private NQCategory(final String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return this.query;
	}

}
