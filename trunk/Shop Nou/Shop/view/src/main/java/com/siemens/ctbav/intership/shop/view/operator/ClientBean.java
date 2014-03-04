package com.siemens.ctbav.intership.shop.view.operator;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertClient;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.exception.operator.ClientWithOrdersException;
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Client deleted", "Client deleted"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
			return;
		}
	}

}
