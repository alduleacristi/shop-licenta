package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.siemens.ctbav.intership.shop.model.Messages;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.MessageService;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.*;

@ManagedBean(name = "messages")
@RequestScoped
public class MessageBean {

	@EJB
	private MessageService ms;
	
	@EJB
	private UserService us;
	
	private List<Messages> messages;
	private Messages selectedMes;
	private String replyMessage;
	private static final String banMessage="We are sorry but your account has been blocked;";
	@PostConstruct
	public void postConstruct(){
		try {
			messages = ms.getAllMesasges();
			selectedMes = messages.get(0);
		} catch (Exception e) {
			messages = new ArrayList<Messages>();
		}
	}

	public Messages getSelectedMes() {
		System.out.println("get selected mes " + selectedMes);
		return selectedMes;
	}

	public void setSelectedMesages(Messages selectedMes) {
		System.out.println("sel message "+ selectedMes);
		this.selectedMes = selectedMes;
	}
	
	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}
	

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}
	public void deleteMessage(Messages message){
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		
		try{
		ms.deleteMessage(message);
		User u = message.getClient().getUser();
		us.banUser(u);
		MailService.sendLink(u.getEmail(), "Bad message", banMessage, false);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"The message has been deleted and the user has been banned; an explanation was sent to the user", ""));
		}
		catch(Exception exc){
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
	
	public void reply(){
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		
		try {
			MailService.sendLink(selectedMes.getClient().getUser().getEmail(), "Reply Shop4J", replyMessage, false);
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			if (user == null)
				return;
			ms.setReplied(selectedMes, user);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"The answer is sent!", ""));
			
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


	
}
