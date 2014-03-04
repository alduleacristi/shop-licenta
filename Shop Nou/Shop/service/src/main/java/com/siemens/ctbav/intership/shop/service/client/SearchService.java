package com.siemens.ctbav.intership.shop.service.client;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.jpa.*;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.siemens.ctbav.intership.shop.model.Product;

@Stateless(name = "searchService")
public class SearchService {

	@PersistenceContext
	private EntityManager em;

	protected Session getSession() {
		return (Session) em.getDelegate();
	}

	@SuppressWarnings("unchecked")
	private List<Product> searchByProductName(String query, String index) {
		FullTextEntityManager fullTextEntityManager = Search
				.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Product.class)
				.overridesForField("productCategory", "productSearchAnalyzer")
				.get();
		
		Query nameQuery = queryBuilder.keyword().onField("productCategory").matching(query).createQuery();

		//Query nameQuery = new TermQuery(new Term(index, query));
		javax.persistence.Query fullTextQuery = fullTextEntityManager
				.createFullTextQuery(nameQuery);

		List<Product> products = fullTextQuery.getResultList();

		return products;
	}

	public List<String> searchForAutoComplete(String query) {
		List<String> results = new ArrayList<String>();
		System.out.println("am intrat in autocomplete cu " + query);
		List<Product> products = searchByProductName(query, "productCategory");
		for (Product p : products)
			if (!results.contains(p.getName())) {
				// System.out.println("!!!!!!!"+p.getName()+"produs");
				results.add(p.getName());
			}

		return results;
	}

	public List<Product> search(String query) {
		System.out.println("am intrat in search cu: " + query);
		List<Product> results = new ArrayList<Product>();
		results.addAll(searchByProductName(query, "productCategory"));
		return results;
	}

	public List<Product> searchCateg(String query) {
		List<Product> results = new ArrayList<Product>();
		results.addAll(searchByProductName(query, "category"));
		return results;
	}

}
