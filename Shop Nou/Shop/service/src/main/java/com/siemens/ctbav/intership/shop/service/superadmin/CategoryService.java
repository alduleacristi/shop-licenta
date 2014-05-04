package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.CategoryName;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.exception.superadmin.CategoryException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;

@Stateless(name = "categoryService")
public class CategoryService {

	@PersistenceContext
	private EntityManager em;

	@EJB
	ColorProductService pcService;

	@EJB
	ProductService pService;

	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		return (List<Category>) em
				.createNamedQuery(Category.GET_ALL_CATEGORIES).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CategoryName> getCategoryName(Long id) {
		return (List<CategoryName>) em
				.createNamedQuery(CategoryName.GET_NAMES_FOR_CATEGORY)
				.setParameter("id", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CategoryName> getCategoryNameForLanguage(Long id,
			String language) {
		return em
				.createNamedQuery(
						CategoryName.GET_NAME_FOR_CATEGORY_AND_LANGUAGE)
				.setParameter("id", id).setParameter("language", language)
				.getResultList();
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

	public void updateCategoryName(long idCategory, String name, String language)
			throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		CategoryName categoryN = getCategoryNameForLanguage(idCategory,
				language).get(0);
		if (categoryN == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		categoryN.setName(name);
		category.setName(getCategoryNameForLanguage(idCategory, "en").get(0)
				.getName());
		em.merge(categoryN);
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
			String name, String language) throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		Category parent = em.find(Category.class, idParent);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		if (parent == null)
			throw new CategoryException(
					"Couldn't find the category's parent in the database");
		category.setCategory(parent);
		CategoryName categoryN = getCategoryNameForLanguage(idCategory,
				language).get(0);
		if (categoryN == null)
			throw new CategoryException(
					"Couldn't find the category in the database");
		categoryN.setName(name);
		category.setName(getCategoryNameForLanguage(idCategory, "en").get(0)
				.getName());
		em.merge(categoryN);
		em.merge(category);
		em.merge(parent);
		em.flush();
	}

	public void removeCategory(long idCategory) throws CategoryException {
		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new CategoryException(
					"Couldn't find the category in the database");

		List<Category> c = storedProcedureGetChildren(idCategory);
		eraseImages(category, c);

		List<CategoryName> cns = getCategoryName(category.getId());
		for (CategoryName cn : cns) {
			em.remove(cn);
		}
		em.flush();
		em.remove(category);
		em.flush();
	}

	private void eraseImages(Category category, List<Category> c) {
		if (c.size() == 0) {

			List<ProductColor> pc = new ArrayList<ProductColor>();
			pc = pcService.getProductsByCategory(category.getId());
			for (ProductColor aux : pc) {
				try {
					pcService.removeProductColor(aux.getId());
				} catch (ColorProductException e1) {
					System.out.println(e1);
				}
			}

			List<Product> p = new ArrayList<Product>();
			p = pService.getGenericProductsByCategory(category.getId());
			for (Product aux : p) {
				try {
					pService.removeProduct(aux.getId());
				} catch (ProductException e) {
					System.out.println(e);
				}
			}
		}
	}

	public void createCategory(List<CategoryName> names, Category category) {
		Category newCategory = new Category(names.get(0).getName(), category);
		em.persist(newCategory);
		em.flush();
		for (int i = 0; i < names.size(); i++) {
			CategoryName cn = names.get(i);
			cn.setId_category(newCategory.getId());
			names.set(i, cn);
		}
		for (CategoryName cn : names)
			em.persist(cn);
	}

	public void createRoot(List<CategoryName> names) {
		Category newCategory = new Category(names.get(0).getName(), null);
		em.persist(newCategory);
		for (int i = 0; i < names.size(); i++) {
			CategoryName cn = names.get(i);
			cn.setId_category(newCategory.getId());
			names.set(i, cn);
		}
		for (CategoryName cn : names)
			em.persist(cn);
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
}
