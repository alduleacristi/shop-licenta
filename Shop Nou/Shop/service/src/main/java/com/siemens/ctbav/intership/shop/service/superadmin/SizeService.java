package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.exception.superadmin.SizeException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Size;

@Stateless(name = "sizeService")
public class SizeService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Size> getSizesByCategory(long id) {
		return (List<Size>) em.createNamedQuery(Size.GET_SIZES_BY_CATEGORY)
				.setParameter("idCategory", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Size> storedProcedureGetParentsSizes(long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(Size.CALL_SIZES_PARENT_CATEGORIES)
				.setParameter("param", id);
		List<Size> list = query.list();
		return list;
	}

	public void removeSize(long idSize) throws SizeException {
		Size size = em.find(Size.class, idSize);
		if (size == null)
			throw new SizeException("Couldn't find the size in the database");
		em.remove(size);
		em.flush();
	}

	public void createSize(String name, Category category) throws SizeException {
		if (category == null)
			throw new SizeException("Couldn't create a new size");
		Size newSize = new Size(name, category);
		em.persist(newSize);
		em.flush();
	}

	public void updateSize(long id, String name) throws SizeException {
		Size size = em.find(Size.class, id);
		if (size == null)
			throw new SizeException("Couldn't find the size in the database");
		size.setSize(name);
		em.merge(size);
		em.flush();
	}

}
