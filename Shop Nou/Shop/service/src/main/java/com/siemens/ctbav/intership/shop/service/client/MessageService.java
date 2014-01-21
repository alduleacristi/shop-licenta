package com.siemens.ctbav.intership.shop.service.client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.NullMessageException;
import com.siemens.ctbav.intership.shop.model.Messages;

@Stateless(name = "messageServiceClient")
public class MessageService {
	@PersistenceContext
	EntityManager em;
	
	public void sendMessage(Messages mesage) throws NullMessageException{
		if(mesage == null)
			throw new NullMessageException("Message is null");
		
		em.persist(mesage);
	}
}
