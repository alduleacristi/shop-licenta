package com.siemens.ctbav.intership.shop.view;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.util.UsersRole;

@RequestScoped
@ManagedBean(name = "errorBean")
@URLMapping(id = "errorBean", pattern = "/error", viewId = "/error.xhtml")
public class ErrorBean {

	private void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Sorry.Description is not availabel."));
		}
	}

	public void goHome() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		

		if (request.isUserInRole(UsersRole.CLIENT.toString())){
			redirect("/Shop4j/client/user/index");
		}

		if (request.isUserInRole(UsersRole.ADMINISTRATOR.toString())){
			
			redirect("/Shop4j/admin/index.xhtml");
		}
		
		if (request.isUserInRole(UsersRole.SUPER_ADMINISTRATOR.toString())){
			redirect("/Shop4j/superadmin/index.xhtml");
		}
		
		if (request.isUserInRole(UsersRole.OPERATOR.toString())){
			redirect("/Shop4j/operator/operatorPage.xhtml");
		}
		
		redirect("/Shop4j/index.xhtml");
	}
}
