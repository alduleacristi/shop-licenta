package com.siemens.ctbav.intership.shop.rest.superadmin;

import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.siemens.ctbav.intership.shop.service.superadmin.*;

@Stateless
@Path("/products")
public class RestProductPhoto {

	@EJB
	PhotoService photoService;

	@GET
	@Path("/{id}/{name}")
	public InputStream getPhotoStream(@PathParam("id") String id,
			@PathParam("name") String name) {
		InputStream is = null;
		is = photoService.getPhotoStreamFromRepository(id, name);
		if (is == null)
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("product.jpg");
		return is;
	}

	@GET
	@Path("/{id}")
	public InputStream getPhotoStream(@PathParam("id") String id) {
		InputStream is = null;
		is = photoService.getPhotoStreamFromRepository(id);
		if (is == null)
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("product.jpg");
		return is;
	}
}
