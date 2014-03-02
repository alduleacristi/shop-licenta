package com.siemens.ctbav.intership.shop.exception.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertClientProduct;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

public class NotEnoughProductsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<ClientProductDTO> productsNotFound= new ArrayList<ClientProductDTO>();

	public NotEnoughProductsException(ClientProduct client) {
		super("Not enough products");
		ClientProductDTO cDTO = ConvertClientProduct.convert(client);
		productsNotFound.add(cDTO);
	}

	public List<ClientProductDTO> getProductsNotFound() {
		return productsNotFound;
	}

}
