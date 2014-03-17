package com.siemens.ctbav.intership.shop.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.client.ClientDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.UserDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.ClientService;
import com.siemens.ctbav.intership.shop.service.UserService;
import com.siemens.ctbav.intership.shop.util.UsersRole;

/**
 * This class is used for page login.xhtml which is a page used for the
 * authentication of users
 * 
 * @author cristian.aldulea
 * 
 */
@ManagedBean(name = "Login")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "login2", pattern = "/Login", viewId = "/login.xhtml") })
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -310064772579735523L;

	/**
	 * This object stored a user used by login method for authentication.
	 */
	@ManagedProperty(value = "#{User}")
	private UserBean user;

	/**
	 * This object is used to get the client witch is logged
	 */
	@EJB
	private ClientService clientService;

	/**
	 * This object is used to get the user witch is logged
	 */
	@EJB
	private UserService userService;

	/**
	 * This filed is used to know if a user is logged or not
	 */
	private boolean logged;

	/**
	 * When the object is created this method verify if the page which make the
	 * request is login If the page is login the verify if a user is logged. If
	 * a user is logged then he method redirect the user to the index page that
	 * correspond with his role.
	 */
	@PostConstruct
	private void redirectIfIsLogged() {

		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServletPath();

		if (path.equals("/login.xhtml")) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			try {
				if (FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("client") != null) {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/Shop4j/client/user/index");
				}

				if (FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("user") != null) {

					if (request
							.isUserInRole(UsersRole.ADMINISTRATOR.toString())) {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/Shop4j/admin/index.xhtml");
					}

					if (request.isUserInRole(UsersRole.OPERATOR.toString())) {
						FacesContext
								.getCurrentInstance()
								.getExternalContext()
								.redirect("/Shop4j/operator/operatorPage.xhtml");
					}

					if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR
							.toString())) {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("/Shop4j/superadmin/index.xhtml");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	/**
	 * This method is used to authenticate a user. If username and password are
	 * correct then the user will be redirect to the page which is specific for
	 * the role of the user. Otherwise a message error will be display.
	 */
	public void login() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			User user = userService
					.getClientByUsername(this.user.getUserName());

			// String hashPass2 = DigestUtils.md5Hex(this.password);
			request.login(this.user.getUserName(), this.user.getPassword());
			this.logged = true;
			System.out.println("s-a facut true");
			if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR.toString())) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("user", user);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("superadmin/index.xhtml");
			}

			if (request.isUserInRole(UsersRole.ADMINISTRATOR.toString())) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("user", user);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("admin/index.xhtml");
			}

			if (request.isUserInRole(UsersRole.OPERATOR.toString())) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("user", user);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("operator/operatorPage.xhtml");
			}

			if (request.isUserInRole(UsersRole.CLIENT.toString())) {
				Client client = clientService.getClientByUsername(this.user
						.getUserName());
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("client", client);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("client/user/index");
			}
		} catch (ServletException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"Incorect User name or password."));
		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"Incorect User name or password."));
		} catch (ClientDoesNotExistException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"The username does not exist."));
		} catch (UserDoesNotExistException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"The username does not exist."));
		} catch (Error e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error ",
					"You are already logged."));
		}
	}

	/**
	 * This method is used to logout the current user. After the execution of
	 * this method the user can not access that pages which are protected.
	 */
	public void logout() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		this.logged = false;
		try {
			request.logout();

			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().remove("client");
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().remove("user");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/Shop4j/index.xhtml");
		} catch (ServletException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method is called when a user press on login menu option. This method
	 * will redirect the user to login page.
	 */
	public void redirectToLogin() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/Shop4j/Login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void forgotPassword() {
		System.out.println("in redirect");
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("forgotPassword.xhtml");
		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "OOps", e.getMessage()));
		}
	}
}
