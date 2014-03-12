package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.superadmin.UserException;
import com.siemens.ctbav.intership.shop.model.User;

@Stateless(name = "operatorsService")
public class OperatorsService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<User> getAllOperators() {
		return (List<User>) em.createNamedQuery(User.GET_ALL_OPERATORS)
				.getResultList();
	}

	public void banOperator(Long id) throws UserException {
		User admin = em.find(User.class, id);
		if (admin == null)
			throw new UserException("User doesn't exists");
		admin.setBan(1);
		em.merge(admin);
		em.flush();
	}

	public void unbanOperator(Long id) throws UserException {
		User admin = em.find(User.class, id);
		if (admin == null)
			throw new UserException("User doesn't exists");
		admin.setBan(0);
		em.merge(admin);
		em.flush();
	}

	public void addOperator(User operator) throws UserException {
		try {
			em.persist(operator);
			em.flush();
		} catch (Exception e) {
			throw new UserException("Username or email already exist");
		}
	}
}
