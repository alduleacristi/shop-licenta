package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.CommandDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Command;

@Stateless(name="commandClient")
public class CommandService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Command> getDeliveredCommandToClient(Long idClient) throws CommandDoesNotExistException{
		List<Command> commands = null;
		
		commands = em.createNamedQuery(Command.GET_CLIENTS_DELIVERED_ORDERS).setParameter("id", idClient).getResultList();
		if(commands.size() == 0)
			throw new CommandDoesNotExistException("Client with Id: "+idClient+" does not have commands.");
		
		return commands;
	}
}
