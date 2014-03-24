package com.siemens.ctbav.intership.shop.view;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.util.UsersRole;
import com.siemens.ctbav.intership.shop.util.client.NavigationUtil;

@ViewScoped
@ManagedBean(name = "errorBean")
@URLMapping(id = "errorBean", pattern = "/error", viewId = "/error.xhtml")
public class ErrorBean {

	public void goHome() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			if (FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("client") != null) {

				NavigationUtil.redirect("/Shop4j/client/user/index");
			} else {
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
				} else {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("/Shop4j/index");

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
