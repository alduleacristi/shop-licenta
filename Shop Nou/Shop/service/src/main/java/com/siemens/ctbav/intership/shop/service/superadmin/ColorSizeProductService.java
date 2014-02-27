package com.siemens.ctbav.intership.shop.service.superadmin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.exception.superadmin.ColorProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorSizeProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.SizeException;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.model.Size;

@Stateless(name = "colorSizeProductService")
public class ColorSizeProductService {
	@PersistenceContext
	private EntityManager em;

	public void addProductColorSize(Long idProdColor, Long nrPieces, Long idSize)
			throws ColorProductException, SizeException {
		ProductColor pc = em.find(ProductColor.class, idProdColor);
		if (pc == null)
			throw new ColorProductException(
					"Product doesn't exists in the database");

		Size size = em.find(Size.class, idSize);
		if (size == null)
			throw new SizeException("Size doesn't exists in the database");

		ProductColorSize pcs = new ProductColorSize(pc, size, nrPieces);
		em.persist(pcs);
		em.flush();
	}

	public void addPieces(Long idProductColorSize, Long nrPieces)
			throws ColorSizeProductException {
		ProductColorSize pcs = em.find(ProductColorSize.class,
				idProductColorSize);
		if (pcs == null)
			throw new ColorSizeProductException(
					"Product doesn't exists in the database");
		pcs.setNrOfPieces(nrPieces + pcs.getNrOfPieces());
		em.merge(pcs);
		em.flush();
	}

	public void editPieces(Long idProductColorSize, Long nrPieces)
			throws ColorSizeProductException {
		ProductColorSize pcs = em.find(ProductColorSize.class,
				idProductColorSize);
		if (pcs == null)
			throw new ColorSizeProductException(
					"Product doesn't exists in the database");
		pcs.setNrOfPieces(nrPieces);
		em.merge(pcs);
		em.flush();
	}

	public void setNrPieces(Long idProductColorSize, Long nrPieces)
			throws ColorSizeProductException {
		ProductColorSize pcs = em.find(ProductColorSize.class,
				idProductColorSize);
		if (pcs == null)
			throw new ColorSizeProductException(
					"Product doesn't exists in the database");
		pcs.setNrOfPieces(nrPieces);
		em.merge(pcs);
		em.flush();
	}

	public void removeProductColorSize(Long idProductColorSize)
			throws ColorSizeProductException {
		ProductColorSize pcs = em.find(ProductColorSize.class,
				idProductColorSize);
		if (pcs == null)
			throw new ColorSizeProductException(
					"Product doesn't exists in the database");
		em.remove(pcs);
		em.flush();
	}

	public ProductColorSize getProductById(Long idProductColorSize) {
		return em.find(ProductColorSize.class, idProductColorSize);
	}

}
