package com.siemens.ctbav.intership.shop.jaxb.operator;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;


@XmlRootElement(name="client")
public class ClientJAXB {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private UserJAXB user;
	private Date lastOrderDate;
	
	public ClientJAXB(){
		
	}
	
	public ClientJAXB(ClientDTO client){
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.phoneNumber = client.getPhoneNumber();
		this.user = new UserJAXB(client.getUser());
		this.lastOrderDate = client.getLastOrderDate();
	}
	public String getFirstName() {
		return firstName;
	}
	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	@XmlElement
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Date getLastOrderDate() {
		return lastOrderDate;
	}
	@XmlElement
	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public UserJAXB getUser() {
		return user;
	}

	@XmlElement
	public void setUser(UserJAXB user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ClientJAXB [firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", user=" + user
				+ ", lastOrderDate=" + lastOrderDate + "]";
	}
	
	
}
