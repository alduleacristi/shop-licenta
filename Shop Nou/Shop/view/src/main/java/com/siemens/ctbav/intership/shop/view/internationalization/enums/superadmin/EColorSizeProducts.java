package com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin;

public enum EColorSizeProducts {
	MESSAGE("message"), CATEGORY("category"), DESCRIPTION("description"), SIZES(
			"sizes"), SUCCESS("success"), ERROR("error"), COLOR("color"), VIEW(
			"view"), AVAILABLE("available"), PIECES_ADDED("piecesAdded"), SIZE_ADDED(
			"sizeAdded"), SELECT_SIZE_ERR("selectSizeErr"), SIZE_DELETED(
			"sizeDeleted"), PIECES_EDITED("piecesEdited"), ERROR_EDIT(
			"errorEdit"), INVALID("invalid"), CHOOSE("choose"), AVAILABLE_SIZES(
			"avaliableS"), NR_PIECES("nrPieces"), ADD("add"), ADD_PIECES(
			"addPieces"), SET_PIECES("setPieces");

	private final String name;

	private EColorSizeProducts(final String query) {
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
