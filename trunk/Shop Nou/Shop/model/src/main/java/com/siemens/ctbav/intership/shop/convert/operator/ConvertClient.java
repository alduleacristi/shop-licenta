package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.ClientDTO;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Command;

public class ConvertClient {

	public static List<ClientDTO> convertClientListDate(List<Client> list) {
		System.out.println("in metoda");
		List<ClientDTO> dtoList = new ArrayList<ClientDTO>();
		for (Client client : list)
			dtoList.add(convertClientWithDate(client));

		return dtoList;

	}
	
	public static ClientDTO convertClient(Client client) {
		UserDTO user = new UserDTO(client.getUser().getUsername(), client
				.getUser().getEmail(), client.getUser().getUserPassword(),
				client.getUser().getRolename(), client.getUser()
						.getPasswordStatus());
		return new ClientDTO(client.getFirstname(), client.getLastname(),
				client.getPhoneNumber(), user);
	}

	public static ClientDTO convertClientWithDate(Client client) {
		UserDTO user = new UserDTO(client.getUser().getUsername(), client
				.getUser().getEmail(), client.getUser().getUserPassword(),
				client.getUser().getRolename(), client.getUser()
						.getPasswordStatus());
		ClientDTO cl = new ClientDTO(client.getFirstname(),
				client.getLastname(), client.getPhoneNumber(), user);
		List<Command> list = client.getCommands();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		Date lastDate = cal.getTime();
		Date aux = lastDate;
		for (Command c : list) {
			System.out.println(c.getOrderDate());
			if (c.getOrderDate().compareTo(lastDate) > 0) {
				System.out.println(c.getOrderDate());
				lastDate = c.getOrderDate();
			}
		}
		if(lastDate.compareTo(aux) == 0)
			cl.setLastOrderDate(null);
		else
		cl.setLastOrderDate(lastDate);

		return cl;
	}

	public static List<ClientDTO> convertClientList(List<Client> list) {

		List<ClientDTO> dtoList = new ArrayList<ClientDTO>();
		for (Client client : list)
			dtoList.add(convertClient(client));

		return dtoList;

	}

	
}