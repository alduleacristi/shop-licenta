package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@ManagedBean(name = "createAccountStep2Bean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "createAccountStep2", pattern = "/client/CreateAccountStep2/", viewId = "/client/CreateAccount/createAccountStep2.xhtml") })
public class CreateAccountStep2 implements Serializable {
	
	private static final long serialVersionUID = 15456L;
	
	@EJB
	private InternationalizationService internationalizationService;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private boolean created;
	
	@PostConstruct
	private void initialize(){
		Boolean c = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("created");
		if (c == null)
			created = false;
		else
			created = c;
		
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
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}
	
	public void createAccount(ActionEvent actionEvent) {
		System.out.println("username: ");
	}
}
