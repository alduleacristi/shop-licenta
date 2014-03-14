package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Adress;

@Stateless(name="AdressServiceClient")
public class AdressService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Adress> getClientAdress(Long idClient){
		List<Adress> adress;
		
		adress = em.createNamedQuery(Adress.GET_CLIENT_ADRESS).setParameter("idClient",idClient).getResultList();
		
		return adress;
	}

}