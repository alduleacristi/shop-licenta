package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.model.User;

public class ConvertUser {

	public static UserDTO convertUser(User user) {
		return new UserDTO(user.getUsername(), user.getEmail(), user.getUserPassword(), user.getRolename(), user.getPasswordStatus());
	}
}
