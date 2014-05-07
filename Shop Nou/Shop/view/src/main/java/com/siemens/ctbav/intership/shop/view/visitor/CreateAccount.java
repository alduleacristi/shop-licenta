package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.codec.digest.DigestUtils;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.UserException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.client.ClientService;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.util.UsersRole;
import com.siemens.ctbav.intership.shop.util.client.NavigationUtil;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "createAccountBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "createAccount", pattern = "/client/CreateAccount/", viewId = "/client/CreateAccount/createAccount.xhtml") })
public class CreateAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InternationalizationService internationalizationService;

	@EJB
	private ClientService clientService;

	private String username;
	private String password;
	private String email;
	private String repassword;

	@PostConstruct
	void initialization() {
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

	public void addUser(ActionEvent actionEvent) {
		FacesMessage msg = null;
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setUserPassword(DigestUtils.md5Hex(password));
		user.setRolename(UsersRole.CLIENT.toString());

		try {
			if (clientService.emailExists(user.getEmail()))
				throw new UserException("Email exists");
			if (clientService.usernameExists(user.getUsername()))
				throw new UserException("Username exists");
		} catch (UserException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			NavigationUtils.addMessage(msg);
			return;
		}

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("createAccountStep2", user);

		NavigationUtil.redirect("/Shop4j/client/CreateAccountStep2/");
	}
}
