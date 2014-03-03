package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "manageAdminsBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "manageAdmins", pattern = "/superadmin/manageAdmins/", viewId = "manageAdmins.xhtml") })
public class ManageAdminsBean implements Serializable {

	private static final long serialVersionUID = 6307678468022334876L;

}
