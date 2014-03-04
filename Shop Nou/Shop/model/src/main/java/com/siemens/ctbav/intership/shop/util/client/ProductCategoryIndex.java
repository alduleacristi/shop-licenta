package com.siemens.ctbav.intership.shop.util.client;

import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.ParameterizedBridge;
import org.hibernate.search.bridge.TwoWayFieldBridge;

import com.siemens.ctbav.intership.shop.model.Product;

public class ProductCategoryIndex implements TwoWayFieldBridge, ParameterizedBridge {
	private String sepChar;

	@Override
	public void set(String name, Object value, Document document,
			LuceneOptions luceneOptions) {
		Product product = (Product) value;
		String productName = product.getName();
		String categoryName = product.getCategory().getName();
		StringBuilder sb = new StringBuilder(100);
		sb.append(productName + this.sepChar + categoryName);
		
		String fieldValue = sb.toString();
		Field field = new Field(name, fieldValue, luceneOptions.getStore(),
				luceneOptions.getIndex(), luceneOptions.getTermVector());
		document.add(field);
	}

	@Override
	public void setParameterValues(Map<String, String> parameters) {
		this.sepChar = (String) parameters.get("sepChar");
		System.out.println("Separatorul este: " + sepChar);
	}

	@Override
	public Object get(String arg0, Document arg1) {
		return arg0;
	}

	@Override
	public String objectToString(Object arg0) {
		return arg0.toString();
	}

}
