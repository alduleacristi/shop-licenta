package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Command;

public class ConvertClient {

	public static ClientDTO convertClient(Client client) {
		UserDTO user = new UserDTO(client.getUser().getUsername(), client.getUser().getEmail(), client.getUser().getUserPassword(), client.getUser().getRolename(), client.getUser().getPasswordStatus());
		return new ClientDTO(client.getFirstname(), client.getLastname(), client.getPhoneNumber(),user);
	}
	
	
	public static ClientDTO convertClientWithDate(Client client){
		UserDTO user = new UserDTO(client.getUser().getUsername(), client.getUser().getEmail(), client.getUser().getUserPassword(), client.getUser().getRolename(), client.getUser().getPasswordStatus());
		ClientDTO cl =  new ClientDTO(client.getFirstname(), client.getLastname(), client.getPhoneNumber(),user);
		List<Command> list = client.getCommands();
		Date lastDate = null;
		for(Command c : list){
			
		}
		
		return cl;
	}
	public static List<ClientDTO> convertClientList(List<Client> list){
		
		List<ClientDTO> dtoList = new ArrayList<ClientDTO>();
		for(Client client : list)
			dtoList.add(convertClient(client));
		
		return dtoList;
			
	}
}