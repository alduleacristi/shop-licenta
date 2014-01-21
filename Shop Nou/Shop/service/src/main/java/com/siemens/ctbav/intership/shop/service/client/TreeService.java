package com.siemens.ctbav.intership.shop.service.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Category;


@Stateless(name = "treeService")
public class TreeService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		return (List<Category>) em
				.createNamedQuery(Category.GET_ALL_CATEGORIES).getResultList();
	}

	public List<Category> getList() {
		List<Category> newList = new ArrayList<Category>();
		Set<Category> categories = new HashSet<Category>(getCategories());

		for (Iterator<Category> iterator = categories.iterator(); iterator
				.hasNext();) {
			Category category = iterator.next();
			if (category.getCategory() == null) {
				newList.add(category);
				iterator.remove();
				break;
			}
		}

		for (int i = 0; i < newList.size(); i++) {
			for (Iterator<Category> iterator = categories.iterator(); iterator
					.hasNext();) {
				Category child = iterator.next();
				if (newList.get(i).getId() == child.getCategory()
						.getId()) {
					newList.add(child);
					iterator.remove();
				}
			}
		}
		return newList;
	}

	/*public void updateCategoryName(long idCategory, String name) {
		Category category = em.find(Category.class, idCategory);
		category.setName(name);
		em.merge(category);
		em.flush();

	}

	public void updateCategoryParent(long idCategory, long idParent) {
		Category category = em.find(Category.class, idCategory);
		Category parent = em.find(Category.class, idParent);
		category.setCategory(parent);
		em.merge(category);
		em.merge(parent);
		em.flush();

	}

	public void updateCategoryNameAndParent(long idCategory, long idParent,
			String name) {
		Category category = em.find(Category.class, idCategory);
		Category parent = em.find(Category.class, idParent);
		category.setCategory(parent);
		category.setName(name);
		em.merge(category);
		em.merge(parent);
		em.flush();
	}

	public void removeCategory(long idCategory) {
		Category category = em.find(Category.class, idCategory);
		em.remove(category);
		em.flush();

	}

	public void createCategory(String name, Category category) {
		Category newCategory = new Category(name, category);
		em.persist(newCategory);
		em.flush();
	}*/

	public void createRoot(String name) {
		Category newCategory = new Category(name, null);
		em.persist(newCategory);
		em.flush();
	}
	
	public Category getCategory(long id) {
		return em.find(Category.class, id);
	}
}
