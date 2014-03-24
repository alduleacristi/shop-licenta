package com.siemens.ctbav.intership.shop.service.superadmin.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public class ExportToCsv implements Exporter {

	@Override
	public void export(File stream, List<ProductColorSize> list) throws FileNotFoundException {
		PrintStream printer = new PrintStream(stream);
		printer.println("Product's name, Product's description, Product's price, Product's reduction, Product's category, Product's color, Product's size, Number of pieces");

		for (ProductColorSize pcs : list) {
			printer.println(pcs.getProductcolor().getProduct().getName()
					+ ", "
					+ pcs.getProductcolor().getProduct().getDescription()
					+ ", "
					+ pcs.getProductcolor().getProduct().getPrice()
					+ ", "
					+ pcs.getProductcolor().getProduct().getReduction()
					+ ", "
					+ pcs.getProductcolor().getProduct().getCategory()
							.getName() + ", "
					+ pcs.getProductcolor().getColor().getName() + ", "
					+ pcs.getSize().getSize() + ", " + pcs.getNrOfPieces());
		}

		printer.close();
	}
}
