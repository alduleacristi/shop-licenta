package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Product;

@Stateless(name = "productServiceClient")
public class ProductService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Product> getReducedProducts() {
		return (List<Product>) em.createNamedQuery(Product.GET_REDUCE_PRODUCTS)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Product getProductById(Long id) throws ProductDoesNotExistException {
		List<Product> products = em.createNamedQuery(Product.GET_PRODUCT_BY_ID)
				.setParameter("id", id).getResultList();

		if (products.size() > 0)
			return products.get(0);
		else
			throw new ProductDoesNotExistException("Product with id: " + id
					+ "does not exist.");
	}

	public void reindex() {
		FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		MassIndexer massIndexer = fullTextEm.createIndexer(Product.class);
		try {
			massIndexer.purgeAllOnStart(true).startAndWait();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		fullTextEm.flushToIndexes();
		// search("bluza");
	}
}
