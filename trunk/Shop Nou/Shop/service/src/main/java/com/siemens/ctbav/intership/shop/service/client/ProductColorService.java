package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.ProductColor;

@Stateless(name = "productColorServiceClient")
public class ProductColorService {
	@PersistenceContext
	private EntityManager em;
	
	public List<ProductColor> getProductsByCategory(Long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(ProductColor.GET_PRODUCTS_FROM_CATEGORY)
				.setParameter("param", id);
		
		@SuppressWarnings("unchecked")
		List<ProductColor> list = query.list();
		return list;
	}
	
	public ProductColor getProductById(Long id) throws ProductDoesNotExistException{
		ProductColor pc = em.find(ProductColor.class, id);
		if(pc == null)
			throw new ProductDoesNotExistException();
		return pc;
	}
}
