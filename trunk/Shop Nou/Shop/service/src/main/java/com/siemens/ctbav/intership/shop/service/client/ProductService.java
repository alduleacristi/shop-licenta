package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Product;

@Stateless(name = "productServiceClient")
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Product> getReducedProducts(){
		return (List<Product>) em
				.createNamedQuery(Product.GET_REDUCE_PRODUCTS).getResultList();
	}
}
