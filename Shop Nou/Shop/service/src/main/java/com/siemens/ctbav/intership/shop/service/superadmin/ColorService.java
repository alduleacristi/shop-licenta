package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Color;

@Stateless(name = "colorService")
public class ColorService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Color> getAllColors() {
		return (List<Color>) em.createNamedQuery(Color.GET_ALL_COLORS)
				.getResultList();
	}

	public Color getColorById(Long id) {
		return em.find(Color.class, id);
	}
}
