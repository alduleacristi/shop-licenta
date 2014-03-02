package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.CategoryDTO;
import com.siemens.ctbav.intership.shop.model.Category;

public class ConvertCategory {

	public static  CategoryDTO convert(Category cat){
		return new CategoryDTO(cat.getName());
	}
}
