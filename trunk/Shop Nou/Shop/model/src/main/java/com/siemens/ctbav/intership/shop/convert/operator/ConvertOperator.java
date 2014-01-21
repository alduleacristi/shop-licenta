package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.OperatorDTO;
import com.siemens.ctbav.intership.shop.model.User;

public class ConvertOperator {

	public static OperatorDTO convertOperator(User user ) {
		return new OperatorDTO(user.getUsername(), user.getUserPassword(),user.getUserPassword(),user.getEmail());
	}
}
