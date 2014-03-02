package com.siemens.ctbav.intership.shop.view.operator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.OrderIDNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.URLNotFoundException;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;
import com.siemens.ctbav.intership.shop.util.operator.AES;

@ManagedBean(name = "cancelOrder")
@RequestScoped
public class CancelOrder {

	@EJB
	private CommandService cmdService;

	public void cancel() {
		String encryptedId= null;
		try {
			encryptedId = getID();
			if (encryptedId == null)
				throw new OrderIDNotFoundException();
			System.out.println(encryptedId);
		} catch (OrderIDNotFoundException exc) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, exc.getMessage(), exc
							.getMessage()));
		}
		
		String decrypt = AES.decrypt(encryptedId);
		Long id = Long.parseLong(decrypt);
		 try {
		 //anuleaza comanda
		 cmdService.cancelOrder(id);
		 } catch (CommandNotFoundException e) {
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage(
		 FacesMessage.SEVERITY_ERROR, e.getMessage(),
		 e.getMessage()));
		 }
	}

	@SuppressWarnings("unused")
	private String getURL() {
		String url = null;
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			url = ((HttpServletRequest) request).getRequestURL().toString();
			System.out.println(((HttpServletRequest) request).getQueryString());
		}
		System.out.println(url);
		return url;
	}

	private String getID() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			return (((HttpServletRequest) request).getQueryString()).substring(3);
		}
		return null;
	}
}
