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
import javax.ws.rs.core.Response.Status;

@Stateless
@Path("/cancelOrder")
public class CancelOrder {

	// 1 criptat
	//Gc73hA6nX3DvakaxTUMUYg==
	@GET
	@Path("/{order}")
	public Response cancelOrder(@PathParam("order") String order,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		
		System.out.println(order);
		String contextPath = request.getContextPath();
		redirectTo(response, contextPath,"/cancelOrder.xhtml?order=" + order);
		return Response.status(Status.ACCEPTED).build();
	}
	
	private void redirectTo(HttpServletResponse response, String contextPath, String to){
		try {
			response.sendRedirect(contextPath+to);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
