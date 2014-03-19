package com.siemens.ctbav.intership.shop.dto.operator;

public class ReturnedProductsDTO {

	
	private ReturnedOrdersDTO order;
	private ProductColorSizeDTO product;
	public ReturnedProductsDTO(ReturnedOrdersDTO comm, ProductColorSizeDTO product) {
		this.order = comm;
		this.product = product;
	}
	
	public ReturnedProductsDTO(){
		
	}
	public ReturnedOrdersDTO getOrder() {
		return order;
	}
	public void setOrder(ReturnedOrdersDTO order) {
		this.order = order;
	}
	public ProductColorSizeDTO getProduct() {
		return product;
	}
	public void setProduct(ProductColorSizeDTO product) {
		this.product = product;
	}
	
	
}
