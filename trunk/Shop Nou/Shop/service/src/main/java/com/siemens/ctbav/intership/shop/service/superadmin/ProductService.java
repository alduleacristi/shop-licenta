package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.dto.superadmin.ProductDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Product;

@Stateless(name = "productService")
public class ProductService {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Product> getProductsByCategory(long id) {
		return (List<Product>) em
				.createNamedQuery(Product.GET_PRODUCTS_BY_CATEGORY)
				.setParameter("id", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getGenericProductsByCategory(long id) {
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery(
				Product.GET_GENERIC_PRODUCTS_FROM_CATEGORY).setParameter(
				"param", id);
		List<Product> list = query.list();
		return list;
	}

	public Product findProduct(long id) {
		return em.find(Product.class, id);
	}

	public void removeProduct(long id) throws ProductException {
		Product product = em.find(Product.class, id);
		if (product == null)
			throw new ProductException(
					"Couldn't find the product in the database");
		em.remove(product);
		em.flush();
	}

	public void createProduct(ProductDTO productDto, long idCategory)
			throws ProductException {
		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new ProductException("Category not found in the database!");
		Product product = new Product(productDto.getName(),
				productDto.getDescription(), productDto.getPrice(), category);
		product.setReduction((double) 0);
		em.persist(product);
		em.flush();
	}

	public void changeParent(long idProduct, long idCategory)
			throws ProductException {
		Product product = em.find(Product.class, idProduct);
		if (product == null)
			throw new ProductException("Product not found in the database");

		Category category = em.find(Category.class, idCategory);
		if (category == null)
			throw new ProductException("Category not found in the database");

		product.setCategory(category);
		em.merge(product);
		em.flush();
	}

	public void updateProduct(long idProduct, Product updated)
			throws ProductException {
		Product product = em.find(Product.class, idProduct);
		if (product == null)
			throw new ProductException("Product not found in the database");
		product.setName(updated.getName());
		product.setPrice(updated.getPrice());
		product.setDescription(updated.getDescription());
		em.merge(product);
		em.flush();
	}

	public void setSale(long idProduct, double sale) throws ProductException {
		Product product = em.find(Product.class, idProduct);
		if (product == null)
			throw new ProductException("Product not found in the database");
		product.setReduction(sale);
		em.merge(product);
		em.flush();
	}
}
