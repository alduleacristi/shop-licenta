package com.siemens.ctbav.intership.shop.dto.operator;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.model.ClientProduct;

public class SelectedClientProduct extends ClientProductDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String [] selected;
//	public boolean isSelected() {
//		return selected;
//	}
//
//	public void setSelected(boolean selected) {
//		System.out.println("selected : " + selected);
//		if (this.selected == false && selected == false) {
//			this.selected = false;
//			return;
//		} 
//		if (this.selected == true && selected == true) {
//			//firstSelection= true;
//			this.selected = false;
//			return;
//		}
//
//		if (this.selected == false && selected == true && firstSelection == true) {
//			this.selected = true;
//			firstSelection= false;
//			return;
//		}
//		if (this.selected == false && selected == true && firstSelection == false) {
//			this.selected = false;
//			firstSelection= false;
//			return;
//		}
//
//	}

	public SelectedClientProduct(ClientProduct cp) {
		super.setNrPieces(cp.getNrPieces());
		super.setPercReduction(cp.getPercRedution());
		super.setPrice(cp.getPrice());
		ProductColorSizeDTO pcs = ConvertProductColorSize.convert(cp
				.getProduct());
		super.setProduct(pcs);
	}

	public String [] getSelected() {
		return selected;
	}

	public void setSelected(String [] selected) {
		this.selected = selected;
	}

}
