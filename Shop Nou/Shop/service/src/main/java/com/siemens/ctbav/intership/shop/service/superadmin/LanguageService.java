package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Language;

@Stateless(name = "languageService")
public class LanguageService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Language> getAllLanguages() {
		return em.createNamedQuery(Language.GET_ALL_LANGUAGES).getResultList();
	}
}
