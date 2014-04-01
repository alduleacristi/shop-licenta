package com.siemens.ctbav.intership.shop.view.client;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.siemens.ctbav.intership.shop.model.ClientProduct;

@FacesConverter("orderConverter")
public class OrderConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("get as object "  + arg2);
		ClientProduct cp = new ClientProduct();
		cp.setIdProductClient((long) Integer.parseInt(arg2));
		return cp;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		System.out.println("get as string " + arg2);
		Long id =(Long) ((arg2 instanceof ClientProduct) ? ((ClientProduct) arg2).getIdProductClient() : null);
		return (id!= null) ? String.valueOf(id) :null;
	}

}
