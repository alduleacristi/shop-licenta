package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.ProductColorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.dto.operator.SizeDTO;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public class ConvertProductColorSize {

	public static ProductColorSizeDTO convert(ProductColorSize pcs){
		ProductColorDTO prCol = ConvertProductColor.convert(pcs.getProductcolor());
		SizeDTO size = ConvertSize.convert(pcs.getSize());
		return new ProductColorSizeDTO(prCol, size, pcs.getNrOfPieces());
	}
	
	public static List<ProductColorSizeDTO> convertList(List<ProductColorSize> list){
		List<ProductColorSizeDTO> l= new ArrayList<ProductColorSizeDTO>();
		for(ProductColorSize pcs : list){
			l.add(convert(pcs));
		}
		
		return l;
	}
}
