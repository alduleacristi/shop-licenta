package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.exception.PhotoException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;

@Stateless(name = "colorProductService")
public class ColorProductService {
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private PhotoService photoService;

	@SuppressWarnings("unchecked")
	public List<ProductColor> getProductsByCategory(Long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(
				ProductColor.GET_PRODUCTS_FROM_CATEGORY).setParameter("param",
				id);
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
		try {
			photoService.removePhotos("id" + id);
		} catch (PhotoException e1) {
			System.out.println(e1);
		}
		em.remove(pc);
		em.flush();
	}

	public void addProductColor(Product p, Color c) {
		System.out.println("add");
		ProductColor newPc = new ProductColor(p, c);
		em.persist(newPc);
		em.flush();
	}

	public void editProductColor(ProductColor pc, Color c)
			throws ColorProductException {
		if (pc == null || c == null)
			throw new ColorProductException("Product or color null");
		pc.setColor(c);
		em.merge(pc);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public ProductColor getProductColorByFields(Product product, Color color) {
		List<ProductColor> pc = em
				.createNamedQuery(ProductColor.GET_COLOR_PRODUCT_BY_FIELDS)
				.setParameter("product", product).setParameter("color", color)
				.getResultList();
		System.out.println(pc.size());
		if (pc.size() > 0)
			return pc.get(0);

		return null;
	}
}
