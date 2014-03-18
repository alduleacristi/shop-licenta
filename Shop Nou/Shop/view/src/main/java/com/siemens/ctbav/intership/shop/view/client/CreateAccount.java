package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.UserException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.client.UserService;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.util.UsersRole;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.superadmin.EManageUsers;

@ManagedBean(name = "createAccountBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "createAccount", pattern = "/client/CreateAccount/", viewId = "createAccount.xhtml") })
public class CreateAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userService;

	@EJB
	private InternationalizationService internationalizationService;

	private String username;
	private String password;
	private String email;
	private String repassword;
	private boolean created;

	@PostConstruct
	void initialization() {
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public void addUser(ActionEvent actionEvent) {
		User user = new User(username, password, UsersRole.CLIENT.toString(),
				email);
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		try {
			userService.addUser(user);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(EManageUsers.SUCCESS
							.getName()),
					internationalizationService
							.getMessage(EManageUsers.USER_ADDED.getName()));
			created = true;
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("created", created);
			create = true;
		} catch (UserException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(EManageUsers.ERROR
							.getName()), e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	public void redirectLogin(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("created");
		NavigationUtils.redirect("/Shop4j/Login");
	}
}
