package com.siemens.ctbav.intership.shop.service.superadmin.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.superadmin.importer.ProductDto;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;

public class ImportCsvProduct implements Importer {

	@Override
	public List<ProductDto> importProduct(InputStream input)
			throws InvalidFileException {
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(
				input));
		return readFile(buffRead);
	}

	private List<ProductDto> readFile(BufferedReader buffRead)
			throws InvalidFileException {
		String line;
		String[] elements;
		int nrOfLine = 1;
		List<ProductDto> products = new ArrayList<ProductDto>();
		try {
			line = buffRead.readLine();
			elements = line.split(",");
			elements = line.split(",");
			if (elements.length != 8)
				throw new InvalidFileException(
						"Number of elements must be 8 at line:" + nrOfLine);
			while ((line = buffRead.readLine()) != null) {
				nrOfLine++;
				elements = line.split(",");
				if (elements.length != 8)
					throw new InvalidFileException(
							"Number of elements must be 8 at line:" + nrOfLine);
				else {
					ProductDto product = new ProductDto();
					product.setName(elements[0]);
					product.setDescription(elements[1]);
					product.setPrice(elements[2]);
					product.setPercReduction(elements[3]);
					product.setCategory(elements[4]);
					product.setColor(elements[5]);
					product.setSize(elements[6]);
					product.setNrPieces(elements[7]);
					products.add(product);
				}

			}
		} catch (IOException e) {
			throw new InvalidFileException("Corrupted file.");
		}
		return products;
	}

}
