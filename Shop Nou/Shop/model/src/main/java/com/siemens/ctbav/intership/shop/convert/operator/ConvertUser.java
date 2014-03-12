package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.enums.operator.PasswordStatus;
import com.siemens.ctbav.intership.shop.model.User;

public class ConvertUser {

	public static UserDTO convertUser(User user) {
		PasswordStatus ps = null;
		if(user == null) return null;
		switch(user.getPasswordStatus()){
		case 1:ps=PasswordStatus.NEW_GENERATED;
		case 2 :ps=PasswordStatus.SAVED;
		}
		if(ps == null) return null;
		
		return new UserDTO(user.getUsername(), user.getEmail(), user.getUserPassword(), user.getRolename(),ps);
	}
}
