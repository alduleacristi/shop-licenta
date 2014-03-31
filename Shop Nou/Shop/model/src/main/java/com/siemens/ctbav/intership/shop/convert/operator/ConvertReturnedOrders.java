package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.model.ReturnedOrders;
import com.siemens.ctbav.intership.shop.model.ReturnedProducts;

public class ConvertReturnedOrders {

	public static ReturnedOrdersDTO convertReturnedOrder(ReturnedOrders order) {
		if (order == null)
			return new ReturnedOrdersDTO();

		CommandDTO cDTO = ConvertCommand.convertCommand(order.getCommand());
	//	List<ReturnedProductsDTO> list = ConvertReturnedProduct.convertList(order.getProductsList());
		return new ReturnedOrdersDTO(order.getId(),cDTO, order.getReturnDate(), order.isRetreived());
	}

	public static List<ReturnedOrdersDTO> convertListOfReturnedOrders(
			List<ReturnedOrders> list) {

		List<ReturnedOrdersDTO> newList = new ArrayList<ReturnedOrdersDTO>();

		if (list == null)
			return newList;

		for (ReturnedOrders order : list) {
			newList.add(convertReturnedOrder(order));
		}

		return newList;
	}
}
