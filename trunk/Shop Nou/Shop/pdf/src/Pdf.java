import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Pdf {

	
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream("d:\\Reports\\pdf.pdf"));
		doc.open();
		doc.addSubject("pdf");
		doc.addSubject("subject");
		doc.addAuthor("author");
		Element el = createTAble();
		doc.add(el);
		doc.close();
	}

	private static Element createTAble() {
		PdfPTable table = new PdfPTable(1);
		table.addCell(new PdfPCell());return table;
		
		
	}
}
