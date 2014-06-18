package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertMessages;
import com.siemens.ctbav.intership.shop.dto.operator.MessageDTO;
import com.siemens.ctbav.intership.shop.model.Messages;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.MessageService;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.*;

@ManagedBean(name = "messages")
@SessionScoped
public class MessageBean {

	@EJB
	private MessageService ms;

	@EJB
	private UserService us;

	private List<MessageDTO> messages;
	private MessageDTO selectedMes = null;
	private String replyMessage;
	private static final String banMessage = "We are sorry but your account has been blocked;";

	@PostConstruct
	public void postConstruct() {
		try {
			messages = ConvertMessages.convertListOfMessages(ms
					.getAllMesasges());
			if (messages.size() == 0)
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"There are no more unreplied messages", ""));
		} catch (Exception e) {
			messages = new ArrayList<MessageDTO>();
		}
	}

	public MessageDTO getSelectedMes() {
		return selectedMes;
	}

	public void setSelectedMesages(MessageDTO selectedMes) throws IOException {
		System.out.println("set selected message " + selectedMes);
		int index = messages.indexOf(selectedMes);
		if (index != -1)
			messages.get(index).setProbablyReplied(true);
		this.selectedMes = selectedMes;
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		context.redirect("messages.xhtml");
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public void deleteMessage(MessageDTO message) {

		System.out.println("in delete message " + message);
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);

		try {
			System.out.println(" in try");
			messages.remove(message);
			ms.deleteMessage(message);
			User u = message.getClient().getUser();
			System.out.println("userul " + u);
			us.banUser(u);
			System.out.println("dupa bann");
			MailService
					.sendLink(u.getEmail(), "Bad message", banMessage, false);
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"The message has been deleted and the user has been banned; an explanation was sent to the user",
									""));
		} catch (Exception exc) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"OOps! the message couldn't be deleted", ""));
		}
		try {
			context.redirect("messages.xhtml");
		} catch (IOException e) {

		}

	}

	public void reply() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);

		try {
			MailService.sendLink(selectedMes.getClient().getUser().getEmail(),
					"Reply Shop4J", replyMessage, false);
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			if (user == null)
				return;
			ms.setReplied(selectedMes, user);
			messages.remove(selectedMes);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"The answer was sent!", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"OOps! the message couldn't be sent", ""));
		}
		try {
			context.redirect("messages.xhtml");
		} catch (IOException e) {

		}

	}

	public void cancel() throws IOException {
		int index = messages.indexOf(selectedMes);
		if (index != -1)
			messages.get(index).setProbablyReplied(false);
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		context.redirect("messages.xhtml");
	}

}
