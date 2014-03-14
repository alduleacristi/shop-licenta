package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

public class GeneratePDF extends GenerateReport {

	public GeneratePDF(List<? extends Object> list) throws IOException {
		super(list);
	}

	@Override
	public void generate() throws IOException {

		if (list == null)
			return;
		try {
			Document doc = new Document();

			PdfWriter.getInstance(doc, new FileOutputStream(getFilename()));
			doc.open();
			doc.addCreationDate();
			doc.addTitle(list.get(0).getClass().getName());
			doc.addAuthor("Iuga Delia");
			Element el = createTable(list);
			doc.add(el);
			doc.close();
		} catch (Exception exc) {
			throw new IOException(exc.getMessage());
		}
	}

	private Element createTable(List<? extends Object> list) throws IOException {

		// System.out.println("list size  "+list.size());
		java.lang.reflect.Field[] fields = list.get(0).getClass()
				.getDeclaredFields();
		if (fields.length == 0)
			return null;
		// aici le fac publice ca sa pot sa le iau valorile
		for (int i = 0; i < fields.length; i++)
			fields[i].setAccessible(true);
		// creez tabel cu atattea coloane cate campuri sunt
		PdfPTable table = new PdfPTable(fields.length);

		for (int i = 0; i < fields.length; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(fields[i].getName()));
			table.addCell(cell);
		}
		table.setWidthPercentage(100);
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			for (int j = 0; j < fields.length; j++) {
				try {
					Object val = fields[j].get(obj);
					PdfPCell cell = null;
					if (val != null) {
						if (val instanceof List && ((List) val).size() > 0) {
							StringBuilder phrase= new StringBuilder();
							for(int k =0; k<((List)val).size(); k++ )
							phrase.append(((List) val).get(k).toString());
							cell = new PdfPCell(new Phrase(phrase.toString()));
						}
						else
						cell = new PdfPCell(new Phrase(val.toString()));
					} else
						cell = new PdfPCell(new Phrase(""));
					table.addCell(cell);
				} catch (Exception e) {
					throw new IOException(e);
				}
			}

		}
		return table;
	}

	@Override
	protected String getFilename() throws ParseException {
		System.out.println(super.getFilename() + ".pdf");
		return super.getFilename() + ".pdf";
	}
}
