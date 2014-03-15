package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.County;

@Stateless(name="CountyServiceClient")
public class CountyService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<County> getCountyOfCountry(Long idCountry){
		List<County> countys;
		
		countys = em.createNamedQuery(County.GET_COUNTY_BY_COUNTRY_ID).setParameter("idCountry", idCountry).getResultList();
		
		return countys;
	}
}
