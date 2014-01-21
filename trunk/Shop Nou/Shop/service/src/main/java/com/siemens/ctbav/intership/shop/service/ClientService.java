package com.siemens.ctbav.intership.shop.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.ClientDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;

@Stateless(name = "clientServiceLogin")
public class ClientService {

	@PersistenceContext
	private EntityManager em;

	public Client getClientByUsername(String username) throws ClientDoesNotExistException {
		@SuppressWarnings("unchecked")
		List<Client> clients = em
				.createNamedQuery(Client.GET_CLIENT_BY_USERNAME)
				.setParameter("username", username).getResultList();
		
		if(clients.size() > 0)
			return clients.get(0);
			
		throw new ClientDoesNotExistException("The client with user: "+username+" does not exist.");
	}
}
