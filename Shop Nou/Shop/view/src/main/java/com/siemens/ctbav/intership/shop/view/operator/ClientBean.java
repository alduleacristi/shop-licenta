package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertClient;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;

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
import com.siemens.ctbav.intership.shop.util.operator.MailService;

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
	private static final String accountdeleted="We are very sorry but your account has been deleted because it hasn't been used in a very long time;";
	private String[] reports;

	public String[] getReports() {
		return reports;
	}

	public void setReports(String[] reports) {
		this.reports = reports;
	}

	private GenerateReport generate;
	
	private List<ClientDTO> filteredClients;


	public List<ClientDTO> getFilteredClients() {
		return filteredClients;
	}

	public void setFilteredClients(List<ClientDTO> filteredClients) {
		this.filteredClients = filteredClients;
	}
	
	@PostConstruct
	public void initClientList() {
		if (allClients != null)
			allClients.clear();
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

		System.out.println("on row toggle");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, ""
				+ event.getVisibility(), "More detalil");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deleteClient(String username) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		try {
		//	System.out.println("username: " +username);
			Long id = userService.getUserId(username);
		//	System.out.println("id: " + id);
			List<CommandDTO> ordersList = ConvertCommand
					.convertListOfOrders(commService.getOrdersForClient(id));
			if (ordersList.size() > 0) {
				System.out.println("There are products ordered by this client; you can't delete it ");
				throw new ClientWithOrdersException(
						"There are products ordered by this client; you can't delete it ");
			}
			else System.out.println("se poate sterge");
			System.out.println("in bean inainte de apel service");
			UserDTO uDTO = ConvertUser.convertUser(userService.getUserByUsername(username));
			System.out.println("inainte de send mail");
			MailService.sendLink(uDTO.getEmail(), "Account deleted", accountdeleted, false);
			userService.deleteUserClient(username);
//			
			
			FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Client deleted; the client has been notified", "Client deleted; the client has been notified"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), "Please try again later."));
			
			return;
		}
		finally{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("clients.xhtml");
			} catch (IOException e) {
				System.out.println("fara redirect");
			}
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
		// for (int i = 0; i < reports.length; i++)
		// System.out.println(reports[i]);
		try {
			if (contains(Reports.PDF.getValue())) {
				generate = new GeneratePDF(allClients);
				generate.generate();
			}

			if (contains(Reports.XML.getValue())) {

				generate = new GenerateXML(allClients);
				// System.out.println(getFilename() + ".xml");
				generate.generate();
			}
			if (contains(Reports.JSON.getValue())) {
				generate = new GenerateJson(allClients);
				generate.generate();
			}
			if (contains(Reports.CSV.getValue())) {
				generate = new GenerateCSV(allClients);
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}


}
