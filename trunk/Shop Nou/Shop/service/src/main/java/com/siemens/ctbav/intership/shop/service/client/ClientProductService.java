package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import com.siemens.ctbav.intership.shop.exception.client.CommandCouldNotPersistException;
import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;

@Stateless(name = "ClientProductServiceForClient")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ClientProductService {
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;

	public void persistClientProducts(List<ClientProduct> clientProducts)
			throws CommandCouldNotPersistException {
		try {
			userTransaction.begin();

			Command cmd = clientProducts.get(0).getCommand();
			Adress adress = cmd.getAdress();
			try {
				em.persist(adress);
				em.persist(cmd);

				for (ClientProduct cp : clientProducts)
					em.persist(cp);
			} catch (Exception e) {
				e.printStackTrace();
				userTransaction.rollback();
				throw new CommandCouldNotPersistException(
						"For the moment we have a problem so we can't take your command. Please try again later.");
			}

			userTransaction.commit();
		} catch (CommandCouldNotPersistException exc) {
			throw exc;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
