package com.siemens.ctbav.intership.shop.util.client;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;

@ManagedBean(name = "productColorSizeConverterClient")
@ViewScoped
public class ProductColorSizeConverter implements Converter,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8394367076005693835L;
	@EJB
	ProductColorSizeService productColorSizeService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		System.out.println("in converter: id="+id);
		try {
			ProductColorSize pcs = productColorSizeService.getProductColorSizeById(Long.parseLong(id));
			System.out.println("a gasit , returneza");
			return pcs;
		} catch (NumberFormatException e) {
			return null;
		} catch (ProductColorSizeDoesNotExistException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object p) {
		Long id = ((ProductColorSize) p).getId();
		return String.valueOf(id);
	}

}
