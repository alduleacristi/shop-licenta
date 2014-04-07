package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import com.siemens.ctbav.intership.shop.exception.client.NullRecommander;
import com.siemens.ctbav.intership.shop.service.util.recommandation.RecommandService_Service;

/**
 * This service make recommendation for a client
 * 
 * @author Cristi
 * 
 */
@Stateless(name = "recommandServiceClient")
public class RecommandService {
	private RecommandService_Service recommandEngine;
	private com.siemens.ctbav.intership.shop.service.util.recommandation.RecommandService recommand;

	@PostConstruct
	private void initialize() {
		try {
			recommandEngine = new RecommandService_Service();
			recommand = recommandEngine.getRecommand();
		} catch (Exception e) {
			recommand = null;
		}
	}

	/**
	 * Get recommendation for a client
	 * 
	 * @param idUser
	 *            id of the client
	 * @return a list wiht recommendated products
	 * @throws NullRecommander
	 */
	public List<Long> getRecommandation(Long idUser) throws NullRecommander {
		if (recommand == null) {
			throw new NullRecommander(
					"Recommander web service can not be acces");
		}
		List<Long> recommandId = recommand.getRecommandation(idUser);
		return recommandId;
	}
	
	public void writePreferences(Long userId,List<Long> itemIds){
		recommand.writePreferences(userId, itemIds);
	}
}
