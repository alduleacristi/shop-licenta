package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.NullClientException;
import com.siemens.ctbav.intership.shop.model.Client;

@Stateless(name = "clientServiceClient")
public class ClientService {
	
	@PersistenceContext
	private EntityManager em;

	public Client getClientByUsername(String username) {
		@SuppressWarnings("unchecked")
		List<Client> clients = em
				.createNamedQuery(Client.GET_CLIENT_BY_USERNAME)
				.setParameter("username", username).getResultList();

		return clients.get(0);
	}
	
	
	
	public void update(Client client) throws NullClientException{
		if(client == null)
			throw new NullClientException("The client object is null.");
		
		em.merge(client);
	}
}