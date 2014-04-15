package com.siemens.ctbav.intership.shop.service.superadmin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siemens.ctbav.intership.shop.dto.superadmin.ProductDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;

@Stateless(name = "productService")
public class ProductService {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ColorProductService pcService;

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

		for (ProductColor pc : product.getProductColor()) {
			try {
				pcService.removeProductColor(pc.getId());
			} catch (ColorProductException e) {
				System.out.println(e);
			}
		}

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

	public void createProduct(String name, String description, Double price,
			Long categId, Double reduction) throws ProductException {
		Category category = em.find(Category.class, categId);
		if (category == null)
			throw new ProductException("Category not found in the database!");
		Product product = new Product(name, description, price, category);
		product.setReduction(reduction);
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

	@SuppressWarnings("unchecked")
	public Product getProductByFields(String name, String description,
			Double price, Long idCategory, Double reduction) {
		List<Product> p = em.createNamedQuery(Product.GET_PRODUCT_BY_FIELDS)
				.setParameter("reduction", reduction)
				.setParameter("idCategory", idCategory)
				.setParameter("price", price)
				.setParameter("description", description)
				.setParameter("name", name).getResultList();
		if (p.size() > 0)
			return p.get(0);
		return null;
	}
}
