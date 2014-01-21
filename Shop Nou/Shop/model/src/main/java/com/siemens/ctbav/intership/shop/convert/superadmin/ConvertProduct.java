package com.siemens.ctbav.intership.shop.convert.superadmin;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.superadmin.ProductDTO;
import com.siemens.ctbav.intership.shop.model.Product;

public class ConvertProduct {

	public static ProductDTO convertProduct(Product product) {
		return new ProductDTO(product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory());
	}

	public static List<ProductDTO> convertProducts(List<Product> products) {
		List<ProductDTO> productsDto = new ArrayList<ProductDTO>();
		for (Product product : products) {
			ProductDTO productDto = convertProduct(product);
			productsDto.add(productDto);
		}
		return productsDto;
	}
}
