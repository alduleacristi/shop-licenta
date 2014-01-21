package com.siemens.ctbav.intership.shop.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.UserDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.User;

@Stateless(name = "userServiceLogin")
public class UserService {
	@PersistenceContext
	private EntityManager em;

	public User getClientByUsername(String username) throws UserDoesNotExistException {
		@SuppressWarnings("unchecked")
		List<User> users = em
				.createNamedQuery(User.GET_CLIENT_BY_USERNAME)
				.setParameter("username", username).getResultList();
		
		if(users.size() > 0)
			return users.get(0);
			
		throw new UserDoesNotExistException("User: "+username+" does not exist.");
}
}
