package com.siemens.ctbav.intership.shop.view.client;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.Client;


@RequestScoped
@ManagedBean(name = "menuBean")
public class MenuBean {
	@PostConstruct
	private void initialize() {
		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		this.name = "Hi " + client.getFirstname() + " " + client.getLastname();
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
