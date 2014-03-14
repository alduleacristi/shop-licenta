package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.jaxb.operator.ClientJAXB;
import com.siemens.ctbav.intership.shop.jaxb.operator.MyJAXBList;
import com.siemens.ctbav.intership.shop.jaxb.operator.OrderJAXB;

public class GenerateXML extends GenerateReport {

	JAXBContext context;
	Marshaller marsh;

	public GenerateXML(List<? extends Object> list)
			throws JAXBException, IOException {
		super(list);
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
			} catch (Exception e) {
				throw new IOException(e);
			}
		else if (obj instanceof CommandDTO)
			try {
				generateForOrders();
			} catch (Exception e) {
				throw new IOException(e);
			}
	}

	private void generateForOrders() throws JAXBException, IOException, ParseException {

		System.out.println("vreau sa generez comenzile");
		List<OrderJAXB> convertedList = convertOrdersToJAXB();
		MyJAXBList lista = new MyJAXBList(convertedList);
		FileWriter flux = new FileWriter(getFilename());
		marsh.marshal(lista, flux);
	}

	private List<OrderJAXB> convertOrdersToJAXB() {
		List<OrderJAXB> lista = new ArrayList<OrderJAXB>();
		for(Object com : list){
			CommandDTO c = (CommandDTO)com;
			OrderJAXB order = new OrderJAXB(c);
			lista.add(order);
		}
		return lista;
	}

	private void generateForClients() throws JAXBException, IOException, ParseException {

		System.out.println("generate for clients");
		List<ClientJAXB> convertedList = convertClientsToJAXB(); 
		MyJAXBList lista = new MyJAXBList(convertedList);
		FileWriter flux = new FileWriter(getFilename());
		marsh.marshal(lista, flux);
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
	
	@Override
	protected String getFilename() throws ParseException{
		return super.getFilename() + ".xml";
	}
}
