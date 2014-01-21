package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Product;

@Stateless(name = "productService")
public class ProductService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Product> getProductsByCategory(long id) {
		return (List<Product>) em
				.createNamedQuery(Product.GET_PRODUCTS_BY_CATEGORY)
				.setParameter("id", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getGenericProductsByCategory(long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(
				Product.GET_GENERIC_PRODUCTS_FROM_CATEGORY).setParameter(
				"param", id);
		List<Product> list = query.list();
		return list;
	}

	public Product findProduct(long id) {
		return em.find(Product.class, id);
	}

	public void removeProduct(long id) throws ProductException {
		Product product = em.find(Product.class, id);
		if (product == null)
			throw new ProductException(
					"Couldn't find the product in the database");
		em.remove(product);
		em.flush();
	}
}
