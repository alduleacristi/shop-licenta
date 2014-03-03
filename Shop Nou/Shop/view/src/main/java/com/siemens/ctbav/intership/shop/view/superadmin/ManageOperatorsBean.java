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
import com.siemens.ctbav.intership.shop.service.superadmin.OperatorsService;

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

}
