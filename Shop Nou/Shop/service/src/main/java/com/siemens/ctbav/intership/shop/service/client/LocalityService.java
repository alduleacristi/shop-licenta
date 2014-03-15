package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Locality;

@Stateless(name="LocalityServiceClient")
public class LocalityService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Locality> getLocalityOfCounty(Long idCounty){
		List<Locality> localitys;
		
		localitys = em.createNamedQuery(Locality.FIND_BY_COUNTY_ID).setParameter("idCounty", idCounty).getResultList();
		
		return localitys;
	}

}
