package com.siemens.ctbav.intership.shop.rest.operator;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertOperator;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.exception.UserException;
import com.siemens.ctbav.intership.shop.service.operator.UserService;

@Stateless
@Path("/passwordRecovery")
public class PasswordRecovery {

	@EJB
	UserService userService;
	
	private UserDTO user;
	
	
	@GET
	@Path("/{password}")
	public void setNewPassword(@PathParam("password") String password) {
		System.out.println(password);
		
		try {
			user = ConvertUser.convertUser(userService.getUserByPassword(password));
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("changePassword.xhtml");
		} catch (UserException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e
							.getMessage()));
		} catch (IOException e) {
			return;
		}
	}
}
