package com.siemens.ctbav.intership.shop.service.superadmin.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public interface Exporter {
	void export(File stream, List<ProductColorSize> list)
			throws FileNotFoundException;
}
