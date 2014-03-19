package com.siemens.ctbav.intership.shop.util.client;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class NavigationUtil {

	public static void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSuccesMessage() {
		FacesMessage msg = (FacesMessage) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("success");
		if (msg != null) {
			addMessage(msg);
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().remove("success");
		}
	}
	
	public static void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
