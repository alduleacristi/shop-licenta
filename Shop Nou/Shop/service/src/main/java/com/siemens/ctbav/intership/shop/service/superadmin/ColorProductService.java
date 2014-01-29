package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;

@Stateless(name = "colorProductService")
public class ColorProductService {
	@PersistenceContext
	private EntityManager em;

	public List<ProductColor> getProductsByCategory(Long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(
				ProductColor.GET_PRODUCTS_FROM_CATEGORY).setParameter("param",
				id);

		@SuppressWarnings("unchecked")
		List<ProductColor> list = query.list();
		return list;
	}

	public ProductColor getProductById(Long id) {
		return em.find(ProductColor.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ProductColor> getColorsForProduct(Long id) {
		return (List<ProductColor>) em
				.createNamedQuery(ProductColor.GET_COLOR_PRODUCTS_FOR_PRODUCT)
				.setParameter("id", id).getResultList();
	}

	public void removeProductColor(long id) throws ColorProductException {
		ProductColor pc = em.find(ProductColor.class, id);
		if (pc == null)
			throw new ColorProductException(
					"No such color for the product in the database");
		em.remove(pc);
		em.flush();
	}

	public void addProductColor(Product p, Color c) {
		ProductColor newPc = new ProductColor(p, c);
		em.persist(newPc);
		em.flush();
	}
}
