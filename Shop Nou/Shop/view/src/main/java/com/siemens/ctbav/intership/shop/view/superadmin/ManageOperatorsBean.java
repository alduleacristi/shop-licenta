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
import com.siemens.ctbav.intership.shop.service.superadmin.OperatorsService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "manageOperatorsBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "manageOperators", pattern = "/superadmin/manageOperators/", viewId = "manageOperators.xhtml") })
public class ManageOperatorsBean implements Serializable {

	private static final long serialVersionUID = 6307678468022334876L;

	@EJB
	private OperatorsService operatorsService;

	private User selectedOperator;
	private List<User> allOperators;
	private List<User> filteredOperators;
	private String username;
	private String password;
	private String email;
	private String repassword;

	@PostConstruct
	void initialization() {
		allOperators = operatorsService.getAllOperators();
	}

	public User getSelectedOperator() {
		return selectedOperator;
	}

	public void setSelectedOperator(User selectedOperator) {
		this.selectedOperator = selectedOperator;
	}

	public List<User> getAllOperators() {
		return allOperators;
	}

	public void setAllOperators(List<User> allOperators) {
		this.allOperators = allOperators;
	}

	public List<User> getFilteredOperators() {
		return filteredOperators;
	}

	public void setFilteredOperators(List<User> filteredOperators) {
		this.filteredOperators = filteredOperators;
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

	public void banOperator(ActionEvent actionEvent) {
		FacesMessage msg = null;
		if (selectedOperator == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				operatorsService.banOperator(selectedOperator.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedOperator.getUsername() + " banned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageOperators");
	}

	public void unbanOperator(ActionEvent actionEvent) {
		FacesMessage msg = null;
		if (selectedOperator == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				operatorsService.unbanOperator(selectedOperator.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedOperator.getUsername() + " unbanned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageOperators");
	}

	public void unbanOperator(User operator) {
		setSelectedOperator(operator);
		FacesMessage msg = null;
		if (selectedOperator == null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No user was selected");
		} else {
			try {
				operatorsService.unbanOperator(selectedOperator.getId());
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
						selectedOperator.getUsername() + " unbanned");
			} catch (UserException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						e.getMessage());
			}
		}
		NavigationUtils.addMessage(msg);
		NavigationUtils.redirect("/Shop4j/superadmin/manageOperators");
	}

	public void addOperator(ActionEvent actionEvent) {
		User operator = new User(username, password, "operator", email);
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		try {
			operatorsService.addOperator(operator);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
					"New operator added!");
			create = true;
			allOperators = operatorsService.getAllOperators();
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
