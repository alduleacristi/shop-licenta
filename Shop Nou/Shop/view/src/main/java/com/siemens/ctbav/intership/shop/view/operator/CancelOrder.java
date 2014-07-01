package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.OrderIDNotFoundException;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;
import com.siemens.ctbav.intership.shop.util.operator.AES;

@ManagedBean(name = "cancelOrder")
@RequestScoped
public class CancelOrder {

	@EJB
	private CommandService cmdService;

	public void cancel() {
		ExternalContext context1 = FacesContext.getCurrentInstance()
				.getExternalContext();
		context1.getFlash().setKeepMessages(true);
		String encryptedId = null;
		
			encryptedId = getID();
//			if (encryptedId == null)
//				throw new OrderIDNotFoundException();
			System.out.println(encryptedId);
		
//		} catch (OrderIDNotFoundException exc) {
//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, exc
//							.getMessage(), exc.getMessage()));
//		}
			
		String decrypt = null;
		if(encryptedId != null) decrypt = AES.decrypt(encryptedId);
		else decrypt="1";
		System.out.println("decript " + decrypt);
		Long id = Long.parseLong(decrypt);
		try {
			// anuleaza comanda
			cmdService.cancelOrder(id);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "The order has been canceled",
					""));
			try {
				context1.redirect("login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (CommandNotFoundException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	private String getID() {
	
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			String queryString = (((HttpServletRequest) request)
					.getQueryString());
			System.out.println("query " + queryString);
			if (queryString == null)
				return null;
			return queryString.substring(6);
		}
		return null;
	}
}
