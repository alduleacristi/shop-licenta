package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.Client;


@RequestScoped
@ManagedBean(name = "menuBean")
public class MenuBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8309676019275165122L;

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
