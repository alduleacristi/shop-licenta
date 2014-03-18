package com.siemens.ctbav.intership.shop.view;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ViewScoped
@ManagedBean(name = "errorBean")
@URLMapping(id = "errorBean", pattern = "/error", viewId = "/error.xhtml")
public class ErrorBean {

	private void redirect(String url) {
		System.out.println("in redirect: " + url);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void goHome() {
		redirect("/Shop4j/Login");
	}
}
