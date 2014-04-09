package com.siemens.ctbav.intership.shop.service.superadmin.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.siemens.ctbav.intership.shop.dto.superadmin.exporter.ProductsDto;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public class ExportToXml implements Exporter {

	@Override
	public void export(File stream, List<ProductColorSize> list)
			throws FileNotFoundException {
		ProductsDto psdto = new ProductsDto(list);
		PrintStream printer = new PrintStream(stream);
		printer.write(100);
		Marshaller marshaller = null;
		try {
			marshaller = prepareMarshaller();
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		if (marshaller == null) {
			System.out.println("marsh null");
			printer.close();
		} else {
			try {
				marshaller.marshal(psdto, stream);
			} catch (JAXBException e) {
				System.out.println(e);
			} finally {
				printer.close();
			}
		}
	}

	private Marshaller prepareMarshaller() throws JAXBException {
		final JAXBContext jaxbContext = JAXBContext
				.newInstance(ProductsDto.class);
		final Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		return marshaller;
	}

}
