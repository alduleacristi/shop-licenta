package com.siemens.ctbav.intership.shop.rest.operator;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Stateless
@Path("/cancelOrder")
public class CancelOrder {

	// 1 criptat
	//Gc73hA6nX3DvakaxTUMUYg==
	@GET
	@Path("/{id}")
	public Response cancelOrder(@PathParam("id") String id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String contextPath = request.getContextPath();
		request.getSession().setAttribute("id", id);
		redirectTo(response, contextPath,"/cancelOrder.xhtml?id=" + id);
		return Response.status(200).entity(id).build();
	}
	
	private void redirectTo(HttpServletResponse response, String contextPath, String to){
		try {
			response.sendRedirect(contextPath+to);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
