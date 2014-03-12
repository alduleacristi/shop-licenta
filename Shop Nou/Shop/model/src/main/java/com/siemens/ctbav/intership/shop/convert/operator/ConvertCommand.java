package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.AdressDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandStatusDTO;
import com.siemens.ctbav.intership.shop.model.Command;

public class ConvertCommand {

	public static CommandDTO convertCommand(Command command){
		AdressDTO adress = ConvertAdress.convertAdress(command.getAdress());
		ClientDTO client= ConvertClient.convertClient(command.getClient());
		CommandStatusDTO status= ConvertCommandStatus.convertStatus(command.getCommand_status());
		List<ClientProductDTO> list = ConvertClientProduct.convertList(command.getClientProducts());
		return new CommandDTO(command.getIdCommand(),command.getOrderDate(), command.getDeliveryDate(), adress, client, status, list);
	}
	
	public static List<CommandDTO> convertListOfOrders(List<Command> list){
		List<CommandDTO> listDTO = new ArrayList<CommandDTO>();
		if(list == null) return null;
		for(Command command : list){
			listDTO.add(ConvertCommand.convertCommand(command));
		}
		return listDTO;
	}
	
}
