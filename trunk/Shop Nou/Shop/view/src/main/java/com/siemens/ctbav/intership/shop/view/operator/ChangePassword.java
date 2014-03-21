package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.siemens.ctbav.intership.shop.exception.operator.UserNotFoundException;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.AES;

@RequestScoped
@ManagedBean(name = "changePass")
public class ChangePassword {

	private String generatedPassword;

	private String newPassword, confirm;

	@EJB
	private UserService userService;

	@PostConstruct
	public void postConstruct() {
		// Map<String, String> params = FacesContext.getCurrentInstance()
		// .getExternalContext().getRequestParameterMap();
		// String pass = params.get("password");
		//
		// if (pass != null) {
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getSessionMap().put("password", pass);
		// generatedPassword = pass;
		// }
		//
		// else
		// generatedPassword = FacesContext.getCurrentInstance()
		// .getExternalContext().getSessionMap().get("password")
		// .toString();

		String decrypt = getGeneratedPasswordfromURL();
		if (decrypt != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("password", decrypt);
		} else
			decrypt = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("password");
		if (decrypt == null)
			return;
		System.out.println(decrypt);
		generatedPassword = AES.decrypt(decrypt);
		System.out.println(generatedPassword);
	}

	private String getGeneratedPasswordfromURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			String queryString = (((HttpServletRequest) request)
					.getQueryString());
			if (queryString == null)
				return null;
			return queryString.substring(9);
		}
		return null;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public void change() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			userService.changePassword(generatedPassword, newPassword);

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"The password has been successfully changed!!!",
					"The password has been successfully changed!!!"));
		} catch (Exception e) {

			System.out.println(e.getMessage());
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	public void backToLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}
}
