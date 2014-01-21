package com.siemens.ctbav.intership.shop.util.superadmin.selectable;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.siemens.ctbav.intership.shop.model.Size;

public class SelectableSize extends ListDataModel<Size> implements
		SelectableDataModel<Size> {

	public SelectableSize() {
	}

	public SelectableSize(List<Size> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Size getRowData(String rowKey) {
		List<Size> sizes = (List<Size>) getWrappedData();

		for (Size car : sizes) {
			if (car.getSize().equals(rowKey))
				return car;
		}
		return null;
	}

	@Override
	public Object getRowKey(Size size) {
		return size.getSize();
	}

}
