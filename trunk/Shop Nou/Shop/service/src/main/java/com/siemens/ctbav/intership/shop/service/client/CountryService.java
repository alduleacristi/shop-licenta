package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Country;

@Stateless(name="CountryServiceClient")
public class CountryService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountrys(){
		List<Country> countrys;
		
		countrys = em.createNamedQuery(Country.findAll).getResultList();
		
		return countrys;
	}
}
