package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.exception.superadmin.CategoryException;

@Stateless(name = "categoryService")
public class CategoryService {

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

		getParent(newList, categories);
		if (!newList.isEmpty())
			getChild(newList, categories);

		return newList;
	}

	private void getChild(List<Category> newList, Set<Category> categories) {
		for (int i = 0; i < newList.size(); i++) {
			for (Iterator<Category> iterator = categories.iterator(); iterator
					.hasNext();) {
				Category child = iterator.next();
				if (child.getCategory() != null) {
					if (newList.get(i).getId() == child.getCategory().getId()) {
						newList.add(child);
						iterator.remove();
					}
				}
			}
		}
	}

	private void getParent(List<Category> newList, Set<Category> categories) {
		for (Iterator<Category> iterator = categories.iterator(); iterator
				.hasNext();) {
			Category category = iterator.next();
			if (category.getCategory() == null) {
				newList.add(category);
				iterator.remove();
				break;
			}
		}
	}

	public void updateCategoryName(long idCategory, String name)
			throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		category.setName(name);
		em.merge(category);
		em.flush();

	}

	public void updateCategoryParent(long idCategory, long idParent)
			throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		Category parent = em.find(Category.class, idParent);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		if (parent == null)
			throw new CategoryException(
					"Couldn't find the category's parent in the database");
		category.setCategory(parent);
		em.merge(category);
		em.merge(parent);
		em.flush();

	}

	public void updateCategoryNameAndParent(long idCategory, long idParent,
			String name) throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		Category parent = em.find(Category.class, idParent);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		if (parent == null)
			throw new CategoryException(
					"Couldn't find the category's parent in the database");
		category.setCategory(parent);
		category.setName(name);
		em.merge(category);
		em.merge(parent);
		em.flush();
	}

	public void removeCategory(long idCategory) throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		em.remove(category);
		em.flush();
	}

	public void createCategory(String name, Category category) {
		Category newCategory = new Category(name, category);
		em.persist(newCategory);
		em.flush();
	}
	/*
	drop procedure if exists shop4j.generic_products_child_categories $$
	create procedure shop4j.generic_products_child_categories(in param bigint)
	begin

		declare found int default 1;

		drop table if exists cat_tree;
		create table cat_tree
		(
			cat_id int primary key
		);

		insert into cat_tree
			select id from category
			where id_parent = param;

		set found = row_count();

		while found > 0 
		do
			insert ignore into cat_tree
				select c_child.id 
				from cat_tree c
					join category c_child
				where c.cat_id = c_child.id_parent;
			set found = row_count();
		end while;

		select p.id, p.id_category, p.name, p.description, p.price
		from product p
		join cat_tree ct on p.id_category = ct.cat_id
		union
		select p.id, p.id_category, p.name, p.description, p.price
		from product p
		join category c on c.id = p.id_category
		where c.id = param;
		select c.id, c.name, c.id_parent 
		from product p 
		join cat_tree ct
		on c.id = ct.cat_id;
		
		drop table cat_tree;

	end $$*/

	public void createRoot(String name) {
		Category newCategory = new Category(name, null);
		em.persist(newCategory);
		em.flush();
	}

	public Category getCategory(long id) {
		return em.find(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Category> storedProcedureGetParents(long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(Category.GET_PARENTS_CATEGORY)
				.setParameter("param", id);
		List<Category> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Category> storedProcedureGetChildren(long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(Category.GET_CHILDREN_CATEGORY)
				.setParameter("param", id);
		List<Category> list = query.list();
		return list;
	}

	/*
	 * public void reindex() { FullTextEntityManager fullTextEm =
	 * Search.getFullTextEntityManager(em); MassIndexer massIndexer =
	 * fullTextEm.createIndexer(Category.class); try {
	 * massIndexer.purgeAllOnStart(true).startAndWait(); } catch
	 * (InterruptedException e) { System.out.println(e.getMessage()); }
	 * fullTextEm.flushToIndexes(); //search("bluza"); }
	 */

}
