package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.CategoryDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductDTO;
import com.siemens.ctbav.intership.shop.model.Product;

public class ConvertProduct {

	public static ProductDTO convert (Product prod){
		CategoryDTO category = ConvertCategory.convert(prod.getCategory());
		return new ProductDTO(prod.getName(), prod.getDescription(), prod.getPrice(), category);
	}
}
