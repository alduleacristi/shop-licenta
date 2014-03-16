package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.CommmandStatusDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.CommandStatus;

@Stateless(name="CommandStatusService")
public class CommandStatusService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public CommandStatus getCommandStatusByName(String commandStatusName) throws CommmandStatusDoesNotExistException{
		List<CommandStatus> list = em.createNamedQuery(CommandStatus.GET_STATUS_BY_NAME).setParameter("status", commandStatusName).getResultList();
		
		if(list == null || list.size() == 0)
			throw new CommmandStatusDoesNotExistException("Command status: "+commandStatusName+" does not exist");
		
		return list.get(0);
	}
}
