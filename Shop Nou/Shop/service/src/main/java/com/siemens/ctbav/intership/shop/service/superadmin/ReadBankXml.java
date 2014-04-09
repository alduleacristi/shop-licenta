package com.siemens.ctbav.intership.shop.service.superadmin;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.siemens.ctbav.intership.shop.dto.superadmin.CubeDTO;
import com.siemens.ctbav.intership.shop.dto.superadmin.CubesDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;

public class ReadBankXml {
	JAXBContext jaxbContext;
	Unmarshaller unMarshall;

	public List<CubeDTO> readCubes(InputStream input)
			throws InvalidFileException {
		CubesDTO products = new CubesDTO();

		try {
			jaxbContext = JAXBContext.newInstance(CubesDTO.class);
			unMarshall = jaxbContext.createUnmarshaller();
			products = (CubesDTO) unMarshall.unmarshal(input);
		} catch (JAXBException e) {
			throw new InvalidFileException(e.getMessage());
		}

		return products.getCubes();
	}
}
