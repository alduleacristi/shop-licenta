package com.siemens.ctbav.intership.shop.convert.superadmin;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.superadmin.CategoryDTO;
import com.siemens.ctbav.intership.shop.dto.superadmin.SizeDTO;
import com.siemens.ctbav.intership.shop.model.Size;

public class ConvertSize {

	public static SizeDTO convertSize(Size size) {
		CategoryDTO categoryDto = ConvertCategory.convertCategory(size
				.getCategory());
		return new SizeDTO(size.getSize(), categoryDto);
	}

	public static List<SizeDTO> convertSizes(List<Size> sizes) {
		List<SizeDTO> sizesDto = new ArrayList<SizeDTO>();
		for (Size size : sizes) {
			sizesDto.add(convertSize(size));
		}
		return sizesDto;
	}
}
