package com.siemens.ctbav.intership.shop.service.operator;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.siemens.ctbav.intership.shop.exception.ClientException;
import com.siemens.ctbav.intership.shop.model.Client;

@Stateless(name = "clientService")
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
	
	@SuppressWarnings("unchecked")
	public List<Client> getAllClients() {
		return em.createNamedQuery(Client.GET_ALL_CLIENTS).getResultList();
	}

	public void deleteClient(Long id) throws ClientException {
		Client c = em.find(Client.class, id);
		if (c == null)
			throw new ClientException("No client found with id " + id);
		em.remove(c);

	}
//	public void deleteClient(Long id) throws ClientException {
//		Client c = em.find(Client.class, id);
//		if (c == null)
//			throw new ClientException("No client found with id " + id);
//		em.remove(c);
//
//	}
	
}
