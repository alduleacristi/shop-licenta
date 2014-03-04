package com.siemens.ctbav.intership.shop.rest.operator;

import java.io.IOException;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.exception.operator.UserNotFoundException;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.Interval;

@Stateless
@Path("/passwordRecovery")
public class PasswordRecovery {

	@EJB
	UserService userService;

	private UserDTO user;

	@GET
	@Path("/{password}/{milis}")
	public Response setNewPassword(@PathParam("password") String password,
			@PathParam("milis") Long milis,
			// @Context ServletContext context,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		// daca a accesat link-ul prea tarziu, atunci e redirectat la pagina de
		// login

		String contextPath = request.getContextPath();
		if (isValidLink(milis) == false) {
			redirectTo(response, contextPath,"/login.xhtml");
			return Response.status(400).entity(milis).build();

		}
		try {
			System.out.println(password);
			user = ConvertUser.convertUser(userService
					.getUserByPassword(password));
			// request.setAttribute("password", password);
			// context.getRequestDispatcher("/operator/changePassword.xhtml").forward(request,
			// response);
		//	response.sendRedirect(contextPath
		//			+ "/changePassword.xhtml?password=" + password);
			redirectTo(response, contextPath,"/changePassword.xhtml?password=" + password);
			return Response.status(Status.ACCEPTED).build();

		} catch (UserNotFoundException e) {
			redirectTo(response, contextPath,"/login.xhtml");
		}
		return Response.status(200).entity(password).build();
	}

	public boolean isValidLink(Long milis) {
		if (Calendar.getInstance().getTimeInMillis() - milis > Interval.DEFAULT.getVal())
			return false;
		return true;
	}
	
	public void redirectTo(HttpServletResponse response, String contextPath, String to){
		try {
			response.sendRedirect(contextPath+to);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}