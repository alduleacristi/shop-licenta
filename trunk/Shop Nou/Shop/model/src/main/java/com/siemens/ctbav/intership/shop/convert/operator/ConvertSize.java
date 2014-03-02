package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.SizeDTO;
import com.siemens.ctbav.intership.shop.model.Size;

public class ConvertSize {

	public static SizeDTO convert(Size size){
		return new SizeDTO(size.getSize());
	}
}
