package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;

@Stateless(name="ClientProductServiceForClient")
public class ClientProductService {
	@PersistenceContext
	private EntityManager em;
	
	public void persistClientProducts(List<ClientProduct> clientProducts){
		Command cmd = clientProducts.get(0).getCommand();
		Adress adress = cmd.getAdress();
		em.persist(adress);
	}
}
