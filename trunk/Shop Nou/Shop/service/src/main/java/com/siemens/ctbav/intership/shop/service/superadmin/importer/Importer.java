package com.siemens.ctbav.intership.shop.service.superadmin.importer;

import java.io.InputStream;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.superadmin.importer.ProductDto;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;

public interface Importer {
	public List<ProductDto> importProduct(InputStream input)
			throws InvalidFileException;
}
