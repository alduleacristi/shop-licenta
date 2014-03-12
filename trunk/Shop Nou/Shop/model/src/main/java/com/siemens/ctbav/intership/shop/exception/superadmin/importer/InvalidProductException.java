package com.siemens.ctbav.intership.shop.exception.superadmin.importer;

import java.util.ArrayList;
import java.util.List;

public class InvalidProductException extends Exception {

	private static final long serialVersionUID = -8381065872174541996L;

	List<Throwable> causes;

	public void addCause(Throwable cause) {
		causes.add(cause);
	}

	public InvalidProductException() {
		causes = new ArrayList<Throwable>();
	}

	public InvalidProductException(String msg) {
		super(msg);
		causes = new ArrayList<Throwable>();
	}

	public List<Throwable> getCauses() {
		return causes;
	}
}
