package com.siemens.ctbav.intership.shop.service.client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

@Stateless(name = "productColorServiceSizeClient")
public class ProductColorSizeService {
	@PersistenceContext
	private EntityManager em;
	
	public ProductColorSize getProductColorSizeById(Long id) throws ProductColorSizeDoesNotExistException{
		ProductColorSize pcs = em.find(ProductColorSize.class, id);
		
		if(pcs == null)
			throw new ProductColorSizeDoesNotExistException("ProductColorSize with id: "+id+" does not exist");
		
		return pcs;
	}
}
