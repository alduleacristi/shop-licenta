package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat.Field;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;

public class GeneratePDF extends GenerateReport {

	public GeneratePDF(List<? extends Object> list, FileWriter stream) {
		super(list, stream);
	}

	@Override
	public void generate() {
		
		Document doc = new Document();
		doc.open();
//		try {
//			doc.add(createTable(list));
//		} catch (Exception e) {
//			throw new IOException(e);
//		}
		doc.close();
	}

	private Element createTable(List<? extends Object> list) throws IllegalArgumentException, IllegalAccessException {
		
		java.lang.reflect.Field[] fields = list.getClass().getFields();
		int nrColumns = fields.length;
		PdfPTable table = new  PdfPTable(nrColumns);
		
		for(int i =0; i<list.size(); i++){
			Object obj = list.get(i);
			for(int j=0; j<nrColumns; j++){
				//String field= fields[j].getName();
				Object value = fields[j].get(obj);
				System.out.println(value);
			}
		}
				
				
				return null;
	}

}
