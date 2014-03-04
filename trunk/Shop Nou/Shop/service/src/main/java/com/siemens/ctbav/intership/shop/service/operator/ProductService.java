package com.siemens.ctbav.intership.shop.service.operator;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;


@Stateless
public class ProductService {

	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public void addProducts(ProductColorSizeDTO product) throws ProductException{
		
		List<ProductColorSize> prod = em.createNamedQuery(ProductColorSize.getProductColorSize).setParameter("color", product.getProdColor().getColor().getName()).setParameter("size", product.getSize().getName()).setParameter("name", product.getProdColor().getProduct().getName()).getResultList();
		if(prod.size() == 0) throw new ProductException("No product was found with this color, size and name");
		if(prod.size() > 1) throw new ProductException("More than one product was found with those characterisitcs");
		
		prod.get(0).setNrOfPieces(prod.get(0).getNrOfPieces() + product.getNrPieces());
		System.out.println(prod.get(0).getNrOfPieces());
		System.out.println(product.getNrPieces());
		//em.merge(prod.get(0));
	}
	
	public void getMissingProducts(){
		List<ProductColorSize> pcsList = em.createNamedQuery(ProductColorSize.GET_ALL_PRODUCTS_COLOR_SIZE).getResultList();
		
	}
}
