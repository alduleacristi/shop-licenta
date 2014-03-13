package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.service.client.AdressService;

@ViewScoped
@ManagedBean(name = "SendCommandBean")
public class SendCommandBean implements Serializable {
	private static final long serialVersionUID = -6618987358166404408L;

	@EJB
	private AdressService adressService;

	private boolean useExistingAdress;
	private List<Adress> userAdresses;
	private String messageUserAdress;

	@PostConstruct
	private void initialize() {
		this.setUseExistingAdress(false);
	}

	public boolean getUseExistingAdress() {
		return useExistingAdress;
	}

	public void setUseExistingAdress(boolean useExistingAdress) {
		this.useExistingAdress = useExistingAdress;
	}

	public List<Adress> getUserAdresses() {
		return userAdresses;
	}

	public void setUserAdresses(List<Adress> userAdresses) {
		this.userAdresses = userAdresses;
	}

	public String getMessageUserAdress() {
		return messageUserAdress;
	}

	public void setMessageUserAdress(String messageUserAdress) {
		this.messageUserAdress = messageUserAdress;
	}

	public void changeUseExistingAdress() {
		if (this.useExistingAdress) {
			Client client = (Client) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("client");

			if (client == null)
				return;

			userAdresses = adressService.getClientAdress(client.getId());
			this.setUseExistingAdress(true);
			System.out.println(userAdresses.size());
			if(userAdresses.size() == 0)
				this.setMessageUserAdress("No records found");
		}else{
			this.setUseExistingAdress(false);
		}
	}

}
