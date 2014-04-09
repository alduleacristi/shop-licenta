package com.siemens.ctbav.intership.shop.service.superadmin.importer;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.siemens.ctbav.intership.shop.dto.superadmin.importer.ProductDto;
import com.siemens.ctbav.intership.shop.dto.superadmin.importer.ProductsDto;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;

@Local
public class ImportXmlProduct implements Importer {
	JAXBContext jaxbContext;
	Unmarshaller unMarshall;

	@Override
	public List<ProductDto> importProduct(InputStream input)
			throws InvalidFileException {
		ProductsDto products = new ProductsDto();

		try {
			jaxbContext = JAXBContext.newInstance(ProductsDto.class);
			unMarshall = jaxbContext.createUnmarshaller();
			products = (ProductsDto) unMarshall.unmarshal(input);
		} catch (JAXBException e) {
			throw new InvalidFileException(e.getMessage());
		}

		return products.getProducts();
	}
}
