package com.siemens.ctbav.intership.shop.rest.superadmin;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Stateless
@Path("/banner")
public class RestBanner {

	@GET
	@Path("/{name}")
	public InputStream getPhotoStream(@PathParam("name") String id) {
		InputStream is = null;
		// is = photoService.getPhotoStreamFromRepository(id);
		if (is == null)
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("n5.jpg");
		return is;
	}

}
