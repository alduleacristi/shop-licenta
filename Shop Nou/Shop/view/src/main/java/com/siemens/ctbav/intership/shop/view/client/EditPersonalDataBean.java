package com.siemens.ctbav.intership.shop.view.client;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.NullClientException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.service.client.ClientService;

@ViewScoped
@ManagedBean(name = "updateClientData")
@URLMappings(mappings = {
		@URLMapping(id = "personalDataClient", pattern = "/client/user/Account/PersonalData", viewId = "/client/user/Account/personalData.xhtml") })
public class EditPersonalDataBean {
	@ManagedProperty(value = "#{updateClient}")
	private ClientBean client;
	
	@EJB
	ClientService clientService;
	
	private Client oldClient;
	
	@PostConstruct
	private void initialize() {
		oldClient = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		client.setFirstName(oldClient.getFirstname());
		client.setLastName(oldClient.getLastname());
		client.setPhone(oldClient.getPhoneNumber());
	}

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}
	
	public void update(){
		oldClient.setFirstname(client.getFirstName());
		oldClient.setLastname(client.getLastName());
		oldClient.setPhoneNumber(client.getPhone());
		
		try {
			clientService.update(oldClient);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Succesful",
					"Your data was succesfully changed."));
		} catch (NullClientException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"Sorry. You can not make modification now. Please try again later"));
		}
	}	
	
}
