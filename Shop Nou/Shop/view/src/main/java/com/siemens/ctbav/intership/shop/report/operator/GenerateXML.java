package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
				generateForClients();
			} catch (JAXBException e) {
				throw new IOException(e);
			}
		else if (obj instanceof CommandDTO)
			generateForOrders();
	}

	private void generateForOrders() {

		System.out.println("vreau sa generez comenzile");
	//	List<OrderJAXB> convertedList = 
	}

	private void generateForClients() throws JAXBException {

		System.out.println("generate for clients");
		List<ClientJAXB> convertedList = convertClientsToJAXB(); 
		MyJAXBList lista = new MyJAXBList(convertedList);
		marsh.marshal(lista, stream);
	}

	private List<ClientJAXB> convertClientsToJAXB() {
		List<ClientJAXB> lista = new ArrayList<ClientJAXB>();
		for(Object cl : list){
			ClientDTO dto = (ClientDTO) cl;
			ClientJAXB c = new ClientJAXB(dto);
			lista.add(c);
		}
		return lista;
	}
}
