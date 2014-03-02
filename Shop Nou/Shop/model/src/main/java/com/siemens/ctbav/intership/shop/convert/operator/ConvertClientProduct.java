package com.siemens.ctbav.intership.shop.convert.operator;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.model.ClientProduct;

public class ConvertClientProduct {

	public static ClientProductDTO convert(ClientProduct cProd) {
		return new ClientProductDTO(cProd.getNrPieces(),
				cProd.getPercRedution(), cProd.getPrice(),
				ConvertProductColorSize.convert(cProd.getProduct()));
	}
	
	public static List<ClientProductDTO> convertList(List<ClientProduct> list ){
		List <ClientProductDTO> listDTO = new ArrayList<ClientProductDTO>();
		for(ClientProduct cp : list){
			ClientProductDTO cpDTO=ConvertClientProduct.convert(cp);
			listDTO.add(cpDTO);
			//System.out.println(cpDTO.getProduct().getNrPieces());
		}
		return listDTO;
	}
}
