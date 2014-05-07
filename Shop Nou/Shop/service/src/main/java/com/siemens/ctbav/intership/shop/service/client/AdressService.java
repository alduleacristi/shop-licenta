package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import com.siemens.ctbav.intership.shop.exception.client.AdressDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Adress;

@Stateless(name="AdressServiceClient")
public class AdressService {
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction userTransaction;
	
	@SuppressWarnings("unchecked")
	public List<Adress> getClientAdress(Long idClient){
		List<Adress> adress;
		
		adress = em.createNamedQuery(Adress.GET_CLIENT_ADRESS).setParameter("idClient",idClient).getResultList();
		
		return adress;
	}
	
	public void addAdress(Adress adress) throws AdressDoesNotExistException{
		try{
			em.persist(adress);
			em.flush();
		}catch(Exception e){
			throw new AdressDoesNotExistException(e.getMessage());
		}
	}
}
