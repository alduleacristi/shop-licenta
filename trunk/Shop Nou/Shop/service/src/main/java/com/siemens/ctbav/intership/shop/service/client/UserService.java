package com.siemens.ctbav.intership.shop.service.client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.NullUserException;
import com.siemens.ctbav.intership.shop.model.User;

@Stateless(name = "userServiceClient")
public class UserService {
	@PersistenceContext
	private EntityManager em;
	
	public void update(User user) throws NullUserException{
		if(user == null)
			throw new NullUserException("The user is null");
		
		em.merge(user);
	}

}
