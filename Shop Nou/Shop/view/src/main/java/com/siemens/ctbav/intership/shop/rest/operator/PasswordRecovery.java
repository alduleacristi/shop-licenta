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
import com.siemens.ctbav.intership.shop.util.operator.AES;

@Stateless
@Path("/passwordRecovery")
public class PasswordRecovery {

	@EJB
	UserService userService;

	private UserDTO user;

	@GET
	@Path("/{milis}/{password}")
	public Response setNewPassword(@PathParam("milis") String milis,
			@PathParam("password") String password,
			// @Context ServletContext context,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		// daca a accesat link-ul prea tarziu, atunci e redirectat la pagina de
		// login
		String contextPath = request.getContextPath();
		String decryptedPass = null;
		Long time = null;
		String decryptedTime = null;

		// incerc sa decriptez datele din url
		// daca utilizatorul a modificat ceva din url, atunci primeste exceptie
		try {
			decryptedPass = AES.decrypt(milis);
			decryptedTime = AES.decrypt(password);
			time = Long.parseLong(decryptedTime);
			System.out.println(decryptedPass + "  " + decryptedTime);
		} catch (Exception exc) {
			System.out
					.println("nu se poate decripta; clientul a modificat stringul");
			return Response.status(400).entity(password).build();
		}
		if (isValidLink(time) == false) {
			redirectTo(response, contextPath, "/login.xhtml");
			return Response.status(400).entity(milis).build();

		}
		// try {
		// user =
		// ConvertUser.convertUser(userService.getUserByPassword(password));
		// } catch (UserNotFoundException e) {
		// System.out.println(e.getMessage());
		// }
		// if (user == null) {
		// System.out.println(" nu am gasit user cu parola " + password);
		// return Response.status(200).entity(password).build();
		// }
				//	 request.setAttribute("password", password);
				//	 context.getRequestDispatcher("/operator/changePassword.xhtml").forward(request,
				//	 response);
					// response.sendRedirect(contextPath
					// + "/changePassword.xhtml?password=" + password);
					redirectTo(response, contextPath, "/changePassword.xhtml?password="
							+ milis);
					return Response.status(Status.ACCEPTED).build();
	//	return Response.status(200).entity(password).build();
	}

	public boolean isValidLink(Long milis) {
		if (Calendar.getInstance().getTimeInMillis() - milis > Interval.DEFAULT
				.getVal())
			return false;
		return true;
	}

	public void redirectTo(HttpServletResponse response, String contextPath,
			String to) {
		try {
			response.sendRedirect(contextPath + to);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
