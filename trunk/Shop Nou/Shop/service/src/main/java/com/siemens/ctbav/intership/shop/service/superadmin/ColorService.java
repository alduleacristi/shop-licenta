package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.superadmin.ColorException;
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

	@SuppressWarnings("unchecked")
	public List<Color> getOtherColors(Long id) {
		return (List<Color>) em.createNamedQuery(Color.GET_OTHER_COLORS)
				.setParameter("id", id).getResultList();
	}

	public Color getColorById(Long id) {
		return em.find(Color.class, id);
	}

	public void updateColorNameAndDescription(Long id, String name,
			String description) throws ColorException {
		Color color = em.find(Color.class, id);
		if (color == null)
			throw new ColorException("Color doesn't exists in the database");
		color.setName(name);
		color.setDescription(description);
		em.merge(color);
		em.flush();
	}

	public void updateColor(Long id, String name, String description,
			String code) throws ColorException {
		Color color = em.find(Color.class, id);
		if (color == null)
			throw new ColorException("Color doesn't exists in the database");
		color.setName(name);
		color.setDescription(description);
		color.setCode(code);
		em.merge(color);
		em.flush();
	}

	public void addColor(String name, String description, String code) {
		Color color = new Color(name, description, code);
		em.persist(color);
		em.flush();
	}

	public void removeColor(Long id) throws ColorException {
		Color color = em.find(Color.class, id);
		if (color == null)
			throw new ColorException("Couldn't find the color in the database");
		em.remove(color);
		em.flush();
	}
}
