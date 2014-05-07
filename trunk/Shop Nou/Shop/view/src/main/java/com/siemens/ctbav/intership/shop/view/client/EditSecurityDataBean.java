package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.NullUserException;
import com.siemens.ctbav.intership.shop.internationalization.enums.client.ESecurityData;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.client.UserService;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

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

	@EJB
	private InternationalizationService internationalizationService;

	private Client oldClient;

	private String oldPassword;

	@PostConstruct
	private void initialize() {
		oldClient = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");

		client.setPassword(oldClient.getUser().getUserPassword());
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
					"internationalization/client/securityData/SecurityData");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/securityData/SecurityData");
		}
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

	public String update() {
		String oldPassHsh = DigestUtils.md5Hex(oldPassword);

		if (!oldClient.getUser().getUserPassword().equals(oldPassHsh)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							internationalizationService
									.getMessage(ESecurityData.ERROR.getName()),
							internationalizationService
									.getMessage(ESecurityData.OLD_PASS_INCORRECT
											.getName())));
			return "";
		}

		User user = oldClient.getUser();
		String clientPassHash = DigestUtils.md5Hex(client.getPassword());
		user.setUserPassword(clientPassHash);

		try {
			userService.update(user);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							internationalizationService
									.getMessage(ESecurityData.SUCCESS.getName()),
							internationalizationService
									.getMessage(ESecurityData.SUCCESS_MESSAGE
											.getName())));
		} catch (NullUserException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							internationalizationService
									.getMessage(ESecurityData.ERROR.getName()),
							internationalizationService
									.getMessage(ESecurityData.INDISPONIBILITY
											.getName())));
		}

		return "";
	}
}
