package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.ColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductDTO;
import com.siemens.ctbav.intership.shop.model.ProductColor;

public class ConvertProductColor {

	public static ProductColorDTO convert (ProductColor prCol){
		ProductDTO prod = ConvertProduct.convert(prCol.getProduct());
		ColorDTO  col= ConvertColor.convert(prCol.getColor());
		return new ProductColorDTO(prod, col);
	}
}
