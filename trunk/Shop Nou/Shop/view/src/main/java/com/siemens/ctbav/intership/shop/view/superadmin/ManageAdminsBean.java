package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.UserException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.superadmin.AdminsService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "manageAdminsBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "manageAdmins", pattern = "/superadmin/manageAdmins/", viewId = "manageAdmins.xhtml") })
public class ManageAdminsBean implements Serializable {

	private static final long serialVersionUID = 6307678468022334876L;

	@EJB
	private AdminsService adminsService;

	private User selectedAdmin;
	private List<User> allAdmins;
	private List<User> filteredAdmins;
	private String username;
	private String password;
	private String email;
	private String repassword;

	@PostConstruct
	void initialization() {
		allAdmins = adminsService.getAllAdmins();
	}

	public User getSelectedAdmin() {
		return selectedAdmin;
	}

	public void setSelectedAdmin(User selectedAdmin) {
		this.selectedAdmin = selectedAdmin;
	}

	public List<User> getAllAdmins() {
		return allAdmins;
	}

	public void setAllAdmins(List<User> allAdmins) {
		this.allAdmins = allAdmins;
	}

	public List<User> getFilteredAdmins() {
		return filteredAdmins;
	}

	public void setFilteredAdmins(List<User> filteredAdmins) {
		this.filteredAdmins = filteredAdmins;
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

	public void banAdmin(ActionEvent actionEvent) {
		FacesMessage msg = null;
		if (selectedAdmin == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				adminsService.banAdmin(selectedAdmin.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedAdmin.getUsername() + " banned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageAdmins");
	}

	public void unbanAdmin(ActionEvent actionEvent) {
		FacesMessage msg = null;
		if (selectedAdmin == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				adminsService.unbanAdmin(selectedAdmin.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedAdmin.getUsername() + " unbanned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageAdmins");
	}

	public void unbanAdmin(User admin) {
		setSelectedAdmin(admin);
		FacesMessage msg = null;
		if (selectedAdmin == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				adminsService.unbanAdmin(selectedAdmin.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedAdmin.getUsername() + " unbanned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageAdmins");
	}

	public void addAdmin(ActionEvent actionEvent) {
		User admin = new User(username, password, "admin", email);
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		try {
			adminsService.addAdmin(admin);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
					"New admin added!");
			create = true;
			allAdmins = adminsService.getAllAdmins();
		} catch (UserException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			create = false;
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}
}
