package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

import com.siemens.ctbav.intership.shop.service.util.recommandation.RecommandService_Service;

@Stateful(name = "recommandServiceClient")
public class RecommandService {
	private RecommandService_Service recommandEngine;
	private com.siemens.ctbav.intership.shop.service.util.recommandation.RecommandService recommand;
	
	@PostConstruct
	private void initialize(){
		recommandEngine = new RecommandService_Service();
		recommand = recommandEngine.getRecommand();
	}
	
	public List<Long> getRecommandation(Long idUser){
		List<Long> recommandId = recommand.getRecommandation(idUser);
		return recommandId;
	}
}
