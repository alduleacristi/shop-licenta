package com.siemens.ctbav.intership.shop.convert.client;

import com.siemens.ctbav.intership.shop.dto.client.ProductColorSizeNumberDTO;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;

/**
 * This class is used to convert from ProductColorSize which is in model to
 * ProductColorSizeNumberDTO and add the number of products which are stored in
 * cart
 * 
 * @author Cristi
 * 
 */
public class ConvertProductColorSizeNumber {
	public static ProductColorSizeNumberDTO convertToProductColorSizeNumberDTO(ProductColorSize productColorSize,Long nrOfPieces){
		ProductColorSizeNumberDTO product = new ProductColorSizeNumberDTO();
		
		product.setColor(productColorSize.getProductcolor().getColor().getName());
		product.setName(productColorSize.getProductcolor().getProduct().getName());
		product.setNrOfPieces(nrOfPieces);
		product.setPrice(productColorSize.getProductcolor().getProduct().getPrice());
		product.setSize(productColorSize.getSize().getSize());
		product.setReduction(productColorSize.getProductcolor().getProduct().getReduction());
		product.setId(productColorSize.getId());
		product.setProductColor(productColorSize.getProductcolor());
		product.setNrOfPiecesInStock(productColorSize.getNrOfPieces());
		
		return product;
	}
	
	public static ProductColorSize convertToProductColorSize(ProductColorSizeNumberDTO productColorSizeDTO ){
		ProductColorSize productColorSize = new ProductColorSize();
		
		productColorSize.setId(productColorSizeDTO.getId());
		productColorSize.setProductcolor(productColorSizeDTO.getProductColor());
		productColorSize.setNrOfPieces(productColorSizeDTO.getNrOfPiecesInStock());
		
		return productColorSize;
	}
}
