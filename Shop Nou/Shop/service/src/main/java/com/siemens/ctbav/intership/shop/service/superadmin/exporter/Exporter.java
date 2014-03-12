package com.siemens.ctbav.intership.shop.service.superadmin.exporter;

import java.io.OutputStream;
import java.util.List;

import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public interface Exporter {
	void export(OutputStream stream, List<ProductColorSize> list);
}
