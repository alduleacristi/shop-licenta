package com.siemens.ctbav.intership.shop.view.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBException;

import org.primefaces.event.ToggleEvent;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertClient;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;

import com.siemens.ctbav.intership.shop.exception.operator.ClientWithOrdersException;
import com.siemens.ctbav.intership.shop.report.operator.GenerateCSV;
import com.siemens.ctbav.intership.shop.report.operator.GenerateJson;
import com.siemens.ctbav.intership.shop.report.operator.GeneratePDF;
import com.siemens.ctbav.intership.shop.report.operator.GenerateReport;
import com.siemens.ctbav.intership.shop.report.operator.GenerateXML;
//import com.siemens.ctbav.intership.shop.exception.operator.ClientWithOrdersException;
import com.siemens.ctbav.intership.shop.service.operator.ClientService;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;
import com.siemens.ctbav.intership.shop.service.operator.UserService;

//@URLMappings(mappings = {@URLMapping(id = "client", pattern = "/operator/clients", viewId = "operator/clients.xhtml") })
@ManagedBean(name = "clients")
@RequestScoped
public class ClientBean {

	@EJB
	private ClientService clientService;
	@EJB
	private CommandService commService;
	@EJB
	private UserService userService;
	private List<ClientDTO> allClients;

	private String[] reports;

	public String[] getReports() {
		return reports;
	}

	public void setReports(String[] reports) {
		this.reports = reports;
	}

	private GenerateReport generate;

	public String getFilename() {
		java.sql.Date currDate = new Date(Calendar.getInstance()
				.getTimeInMillis());
		return "d:\\Reports\\clients" + currDate;
	}

	@PostConstruct
	public void initClientList() {
		setAllClients(ConvertClient.convertClientListDate(clientService
				.getAllClients()));

	}

	public void setAllClients(List<ClientDTO> allClients) {
		this.allClients = allClients;
	}

	public List<ClientDTO> getAllClients() {
		return allClients;
	}

	public void onRowToggle(ToggleEvent event) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, ""
				+ event.getVisibility(), "More detalil");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deleteClient(String username) {
		try {
			Long id = userService.getUserId(username);
			System.out.println(id);
			List<CommandDTO> ordersList = ConvertCommand
					.convertListOfOrders(commService.getOrdersForClient(id));
			if (ordersList.size() > 0) {
				throw new ClientWithOrdersException(
						"There are products ordered by this client; you can't delete it ");
			}
			userService.deleteUserClient(username);
			FacesContext.getCurrentInstance().getExternalContext()
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Client deleted", "Client deleted"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
			return;
		}
	}

	private boolean contains(int val) {
		for (int i = 0; i < reports.length; i++) {
			int value = Integer.parseInt(reports[i]);
			if (value == val)
				return true;
		}
		return false;
	}

	public void generateReports() {
	//	for (int i = 0; i < reports.length; i++)
		//	System.out.println(reports[i]);
		try {
			if (contains(Reports.PDF.getValue())) {
				generate = new GeneratePDF(allClients, new FileWriter(
						getFilename() + ".pdf"));
				generate.generate();
			}

			if (contains(Reports.XML.getValue())) {

				generate = new GenerateXML(allClients, new FileWriter(
						getFilename() + ".xml"));
			//	System.out.println(getFilename() + ".xml");
				generate.generate();
			}
			if (contains(Reports.JSON.getValue())) {
				generate = new GenerateJson(allClients, new FileWriter(
						getFilename() + ".json"));
				generate.generate();
			}
			if (contains(Reports.CSV.getValue())) {
				generate = new GenerateCSV(allClients, new FileWriter(
						getFilename() + ".csv"));
				generate.generate();
			}
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"The reports have been generated in folder Reports",
									""));
		} catch (Exception e) {
			 System.out.println("exceptie " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

}
