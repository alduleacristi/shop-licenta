package com.siemens.ctbav.intership.shop.service.client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.ClientProduct;

@Stateless(name="ClientProductServiceForClient")
public class ClientProductService {
	@PersistenceContext
	private EntityManager em;
	
	public void addClientProduct(ClientProduct clientProduct){
		em.persist(clientProduct);
	}
}
