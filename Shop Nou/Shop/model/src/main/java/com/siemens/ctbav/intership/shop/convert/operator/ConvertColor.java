package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.ColorDTO;
import com.siemens.ctbav.intership.shop.model.Color;

public class ConvertColor {
	public static ColorDTO convert(Color col){
		return new ColorDTO(col.getName());
	}
}
