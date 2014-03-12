package com.siemens.ctbav.intership.shop.view.operator;

public enum Reports {

	PDF(1), XML(2), JSON(3), CSV(4);

	private Integer value;

	private Reports(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
