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
import com.siemens.ctbav.intership.shop.exception.client.NullUserException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.client.UserService;

@ViewScoped
@ManagedBean(name = "updateClientSecurityData")
@URLMappings(mappings = { @URLMapping(id = "securityDataClient", pattern = "/client/user/Account/SecurityData", viewId = "/client/user/Account/securityData.xhtml") })
public class EditSecurityDataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4408845936377972540L;

	@ManagedProperty(value = "#{updateClient}")
	private ClientBean client;

	@EJB
	UserService userService;

	private Client oldClient;

	private String oldPassword;

	@PostConstruct
	private void initialize() {
		oldClient = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");

		client.setPassword(oldClient.getUser().getUserPassword());
	}

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void update() {
		if (!oldClient.getUser().getUserPassword().equals(oldPassword)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"The old password is incorect."));
			return;
		}

		User user = oldClient.getUser();
		user.setUserPassword(client.getPassword());

		try {
			userService.update(user);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Succesful",
					"Your password was succesfully changed."));
		} catch (NullUserException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ",
							"Sorry. You can not make modification now. Please try again later"));
		}
	}
}
