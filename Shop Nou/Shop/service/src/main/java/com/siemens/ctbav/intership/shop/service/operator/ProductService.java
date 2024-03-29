package com.siemens.ctbav.intership.shop.service.operator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.dto.operator.MissingProduct;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.operator.ProductException;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

@Stateless
public class ProductService {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	public void addProducts(MissingProduct pr)
			throws ProductException {
		ProductColorSizeDTO product = pr.getProduct();
		List<ProductColorSize> prod = em
				.createNamedQuery(ProductColorSize.GET_PRODUCT_COLOR_SIZE)
				.setParameter("color",
						product.getProdColor().getColor().getName())
				.setParameter("size", product.getSize().getName())
				.setParameter("name",
						product.getProdColor().getProduct().getName())
				.getResultList();
		if (prod.size() == 0)
			throw new ProductException(
					"No product was found with this color, size and name");
		if (prod.size() > 1)
			throw new ProductException(
					"More than one product was found with those characterisitcs");

		prod.get(0).setNrOfPieces(
				prod.get(0).getNrOfPieces() +pr.getPiecesAdded());
		System.out.println(prod.get(0).getNrOfPieces());
		System.out.println(product.getNrPieces());
	    em.merge(prod.get(0));
	}

	@SuppressWarnings("unchecked")
	public Set<MissingProduct> getMissingProducts() throws ProductException {
		List<ProductColorSize> pcsList = em.createNamedQuery(
				ProductColorSize.GET_ALL_PRODUCTS_COLOR_SIZE).getResultList();
		Set<MissingProduct> missList = new TreeSet<MissingProduct>();

		if (pcsList == null || pcsList.size() == 0)
			throw new ProductException("There are no products in the database");

		for (ProductColorSize pcs : pcsList) {
			System.out.println(pcs);
			long nrPiecesLeft = pcs.getNrOfPieces();
			try {
				List<ClientProduct> list = getClientOrders(pcs);
				for (ClientProduct cp : list) {
					nrPiecesLeft -= cp.getNrPieces();
				}
				//inmultesc cu -1, deci o sa apara valoare negativa daca exista destule produse pe stoc
				//altfel o sa apara valoare pozitiva.
				missList.add(new MissingProduct(ConvertProductColorSize
						.convert(pcs), nrPiecesLeft * (-1)));
			} catch (ProductException exc) {

			}

		}

		return missList;
	}

	@SuppressWarnings("unchecked")
	public List<ClientProduct> getClientOrders(ProductColorSize pcs)
			throws ProductException {

		List<ClientProduct> list = em
				.createNamedQuery(
						ClientProduct.GET_CLIENT_PRODUCTS_FOR_PRODUCTS)
				.setParameter("id", pcs.getId()).getResultList();
		if (list == null || list.size() == 0)
			throw new ProductException("This product was never ordered");
		return list;
	}
}
