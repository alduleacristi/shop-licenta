package com.siemens.ctbav.intership.shop.util.client;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("NrOfPiecesConverter")
public class NumberOfPiecesConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String numberOfPieces) {
		
		try{
			Integer nr = Integer.valueOf(numberOfPieces);
			if(nr <= 0)
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","The number of pieces must be great then one."));
			return nr;
		}catch(Exception exc){
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","The number of pieces is not valid."));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object numberOfPieces) {
		return numberOfPieces.toString();
	}

}
