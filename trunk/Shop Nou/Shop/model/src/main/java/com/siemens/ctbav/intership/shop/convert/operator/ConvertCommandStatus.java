package com.siemens.ctbav.intership.shop.convert.operator;

import com.siemens.ctbav.intership.shop.dto.operator.CommandStatusDTO;
import com.siemens.ctbav.intership.shop.model.CommandStatus;

public class ConvertCommandStatus {

	public static CommandStatusDTO convertStatus(CommandStatus status){
		return new CommandStatusDTO(status.getStatus());
	}
}
