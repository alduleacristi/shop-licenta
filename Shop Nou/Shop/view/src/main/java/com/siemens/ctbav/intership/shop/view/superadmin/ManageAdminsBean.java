package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.superadmin.AdminsService;

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

}
