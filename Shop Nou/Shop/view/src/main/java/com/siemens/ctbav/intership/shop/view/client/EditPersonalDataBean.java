package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

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
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.EPersonalData;

@ViewScoped
@ManagedBean(name = "updateClientData")
@URLMappings(mappings = { @URLMapping(id = "personalDataClient", pattern = "/client/user/Account/PersonalData", viewId = "/client/user/Account/personalData.xhtml") })
public class EditPersonalDataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4950013606934801190L;

	@ManagedProperty(value = "#{updateClient}")
	private ClientBean client;

	@EJB
	ClientService clientService;

	@EJB
	private InternationalizationService internationalizationService;

	private Client oldClient;

	@PostConstruct
	private void initialize() {
		oldClient = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		client.setFirstName(oldClient.getFirstname());
		client.setLastName(oldClient.getLastname());
		client.setPhone(oldClient.getPhoneNumber());
		internationalizationInit();
	}

	private void internationalizationInit() {
		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/personalData/PersonalData");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/personalData/PersonalData");
		}
	}

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}

	public String update() {
		oldClient.setFirstname(client.getFirstName());
		oldClient.setLastname(client.getLastName());
		oldClient.setPhoneNumber(client.getPhone());

		try {
			clientService.update(oldClient);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							internationalizationService
									.getMessage(EPersonalData.SUCCESS.getName()),
							internationalizationService
									.getMessage(EPersonalData.SUCCESS_MESSAGE
											.getName())));
		} catch (NullClientException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							internationalizationService
									.getMessage(EPersonalData.ERROR.getName()),
							internationalizationService
									.getMessage(EPersonalData.ERROR_MESSAGE
											.getName())));
		}

		return "";
	}

}
