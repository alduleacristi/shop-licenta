package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "messages")
@NamedQueries({
	@NamedQuery(name = Messages.GET_UNREPLIED_MESSAGES, query = "SELECT m FROM Messages m where m.isReplied = :isReplied")})
public class Messages implements Serializable{

	private static final long serialVersionUID = 408004164350412012L;
	
	public final static String GET_UNREPLIED_MESSAGES = "getUnrepliedMessages";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String subject;
	
	@Column
	private String message;
	
	@Column(name = "send_date")
	private Date sendDate;
	
	@Column(name = "is_replied")
	private Boolean isReplied;
	
	@OneToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@OneToOne
	@JoinColumn(name="id_client")
	private Client client;
	
	public Messages(){
		
	}
	
	public Messages(String subject, String message,
			Date sendDate, User user, Client client) {
		this.subject = subject;
		this.message = message;
		this.sendDate = sendDate;
		this.isReplied = false;
		this.user = user;
		this.client = client;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Boolean isReplied() {
		return isReplied;
	}

	public void setReplied(Boolean isReplied) {
		this.isReplied = isReplied;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", subject=" + subject + ", message="
				+ message + ", sendDate=" + sendDate + ", isReplied="
				+ isReplied + ", user=" + user + ", client=" + client + "]";
	}

}