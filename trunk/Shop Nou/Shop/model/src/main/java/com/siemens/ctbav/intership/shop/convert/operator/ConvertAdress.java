package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.AdressDTO;
import com.siemens.ctbav.intership.shop.model.Adress;

public class ConvertAdress {

	public static AdressDTO convertAdress(Adress adress){
		return new AdressDTO(adress.getLocality().getName(), adress.getStaircase(), adress.getNumber(), adress.getBlock(), adress.getFlat());
	}
}
