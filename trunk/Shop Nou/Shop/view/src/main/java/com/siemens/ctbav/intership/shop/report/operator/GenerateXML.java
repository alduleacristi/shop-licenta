package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.jaxb.operator.ClientJAXB;
import com.siemens.ctbav.intership.shop.jaxb.operator.MyJAXBList;

public class GenerateXML extends GenerateReport {

	JAXBContext context;
	Marshaller marsh;

	public GenerateXML(List<? extends Object> list, FileWriter stream)
			throws JAXBException {
		super(list, stream);
		context = JAXBContext.newInstance(MyJAXBList.class);
		marsh = context.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}

	@Override
	public void generate() throws IOException {

		if (list == null || list.size() == 0)
			return;

		Object obj = list.get(0);

		if (obj instanceof ClientDTO)
			try {
				generateForClients(list, stream);
			} catch (JAXBException e) {
				throw new IOException(e);
			}
		else if (obj instanceof CommandDTO)
			generateForOrders(list, stream);
	}

	private void generateForOrders(List<? extends Object> list,
			FileWriter stream) {

	}

	private void generateForClients(List<? extends Object> list,
			FileWriter stream) throws JAXBException {
		
		MyJAXBList lista = new MyJAXBList();
		lista.setList(list);
		marsh.marshal(lista, System.out);
		marsh.marshal(lista, stream);
//		for (int i = 0; i < list.size(); i++) {
//			ClientJAXB client = new ClientJAXB((ClientDTO) list.get(i));
//			System.out.println(client);
//			marsh.marshal(client, stream);
//			marsh.marshal(client, System.out);
//		}

	}
}
