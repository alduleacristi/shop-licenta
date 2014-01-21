package com.siemens.ctbav.intership.shop.convert.superadmin;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.superadmin.CategoryDTO;
import com.siemens.ctbav.intership.shop.model.Category;

public class ConvertCategory {

	public static CategoryDTO convertCategory(Category category) {
		if (category.getCategory() != null)
			return new CategoryDTO(category.getName(), new CategoryDTO(category
					.getCategory().getName(), null));
		return new CategoryDTO(category.getName(), null);
	}

	public static List<CategoryDTO> convertCategories(List<Category> categories) {
		List<CategoryDTO> categoriesDto = new ArrayList<CategoryDTO>();
		for (Category category : categories) {
			categoriesDto.add(convertCategory(category));
		}
		return categoriesDto;
	}
}
