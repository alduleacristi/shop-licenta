package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.NullMessageException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Messages;
import com.siemens.ctbav.intership.shop.service.client.MessageService;

@ViewScoped
@ManagedBean(name = "messageBeanClient")
@URLMappings(mappings = { @URLMapping(id = "message", pattern = "/client/user/contact", viewId = "/client/user/contact.xhtml") })
public class MessageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2073951724665061782L;

	@EJB
	private MessageService messageService;

	private String message, subject;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void sendMessage() {
		Messages messageObj = new Messages();
		messageObj.setSubject(subject);
		messageObj.setMessage(message);
		messageObj.setReplied(false);

		Date sendDate = new Date();
		messageObj.setSendDate(sendDate);

		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");
		messageObj.setClient(client);

		try {

			messageService.sendMessage(messageObj);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Succesful",
					"Your password was succesfully send."));

		} catch (NullMessageException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Erro",
					"Your can not send a message now. Please try again later."));
		}
	}
}
