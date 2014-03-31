package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.model.ReturnedProducts;

public class ConvertReturnedProduct {

	
	public static ReturnedProductsDTO convertReturnedProduct(ReturnedProducts prod){
		if(prod == null) return new ReturnedProductsDTO();
		
		ReturnedOrdersDTO comm = ConvertReturnedOrders.convertReturnedOrder(prod.getCommand());
		ProductColorSizeDTO product = ConvertProductColorSize.convert(prod.getProduct());
		
		return new ReturnedProductsDTO(prod.getIdreturnedproducts(),comm, product);
	}
	
	public static List<ReturnedProductsDTO> convertList(List<ReturnedProducts> list){
		List<ReturnedProductsDTO> lista = new ArrayList<ReturnedProductsDTO>();
		
		if(list ==  null) return lista;
		
		for(ReturnedProducts pr : list){
			lista.add(convertReturnedProduct(pr));
		}
		
		return lista;
	}
}
