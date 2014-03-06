package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.User;

@Stateless(name = "adminsService")
public class AdminsService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<User> getAllAdmins() {
		return (List<User>) em.createNamedQuery(User.GET_ALL_ADMINS)
				.getResultList();
	}
}