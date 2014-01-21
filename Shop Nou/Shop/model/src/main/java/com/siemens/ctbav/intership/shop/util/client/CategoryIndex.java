package com.siemens.ctbav.intership.shop.util.client;

import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.ParameterizedBridge;

import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Product;

public class CategoryIndex implements FieldBridge, ParameterizedBridge {
	private String sepChar;

	@Override
	public void set(String name, Object value, Document document,
			LuceneOptions luceneOptions) {
		Product product = (Product) value;
		//String productName = product.getName();
		String categoryName = product.getCategory().getName();
		StringBuilder sb = new StringBuilder(100);
		sb.append(categoryName);
		for (Category c : product.getCategory().getCategories()) {
			sb.append(this.sepChar + c.getName());
		}
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

}
