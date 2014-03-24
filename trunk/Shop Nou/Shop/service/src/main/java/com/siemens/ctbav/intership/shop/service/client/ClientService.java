package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.UserException;
import com.siemens.ctbav.intership.shop.exception.client.ClientDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.NullClientException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.User;

@Stateless(name = "clientServiceClient")
public class ClientService {

	@PersistenceContext
	private EntityManager em;

	public Client getClientByUsername(String username)
			throws ClientDoesNotExistException {
		@SuppressWarnings("unchecked")
		List<Client> clients = em
				.createNamedQuery(Client.GET_CLIENT_BY_USERNAME)
				.setParameter("username", username).getResultList();

		if (clients.size() > 0)
			return clients.get(0);
		else
			throw new ClientDoesNotExistException("Client with username: "
					+ username + " does not exist.");
	}

	@SuppressWarnings("unchecked")
	public boolean usernameExists(String username) {
		List<User> clients = em.createNamedQuery(User.GET_CLIENT_BY_USERNAME)
				.setParameter("username", username).getResultList();
		if (clients.size() > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean emailExists(String email) {
		List<User> clients = em.createNamedQuery(User.GET_CLIENT_BY_EMAIL)
				.setParameter("email", email).getResultList();
		if (clients.size() > 0)
			return true;
		return false;
	}

	public void update(Client client) throws NullClientException {
		if (client == null)
			throw new NullClientException("The client object is null.");

		em.merge(client);
	}

	public void addClient(Client client) throws UserException {
		try {
			em.persist(client.getUser());
			client.setId(client.getUser().getId());
			em.persist(client);
			em.flush();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new UserException("Invalid user");
		}

	}
}