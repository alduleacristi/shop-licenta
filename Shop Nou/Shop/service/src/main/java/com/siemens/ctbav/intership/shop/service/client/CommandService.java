package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.exception.client.CommandDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Command;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.model.ReturnedOrders;
import com.siemens.ctbav.intership.shop.model.ReturnedProducts;

@Stateless(name = "commandClient")
public class CommandService {

	@PersistenceContext
	private EntityManager em;

	
	@SuppressWarnings("unchecked")
	public List<Command> getDeliveredCommandToClient(Long idClient)
			throws CommandDoesNotExistException {
		List<Command> commands = null;

		commands = em.createNamedQuery(Command.GET_CLIENTS_DELIVERED_ORDERS)
				.setParameter("id", idClient).getResultList();
		if (commands.size() == 0)
			throw new CommandDoesNotExistException("Client with Id: "
					+ idClient + " does not have commands.");

		return commands;
	}





public void addCommand(Command command) {
		em.persist(command);
	}

	@SuppressWarnings("unchecked")
	public List<Command> getClientOrders(Long idClient)
			throws CommandDoesNotExistException {
		List<Command> commands = null;

		commands = em.createNamedQuery(Command.GET_ALL_ORDERS_FOR_CLIENT)
				.setParameter("id", idClient).getResultList();
		if (commands.size() == 0)
			throw new CommandDoesNotExistException("Client with Id: "
					+ idClient + " does not have commands.");

		return commands;
	}





















	@SuppressWarnings("unchecked")
	public void addProductToReturn(ReturnedProductsDTO product)
			throws CommandDoesNotExistException,
			ProductColorSizeDoesNotExistException {

		// verific ca parametri sa nu fie nuli
		if (product == null || product.getOrder() == null
				|| product.getOrder().getCommand() == null)
			throw new CommandDoesNotExistException(
					"There is no order or product to return");
		// caut comanda in baza in tabelul ReturnedOrders
		// daca nu exista adaug comanda
		List<ReturnedOrders> list = em
				.createNamedQuery(ReturnedOrders.getReturnedOrder)
				.setParameter("id", product.getOrder().getCommand().getId())
				.getResultList();
		if (list == null)
			throw new CommandDoesNotExistException();

		// caut produsul in baza
		List<ProductColorSize> products = em
				.createNamedQuery(ProductColorSize.GET_PRODUCT_COLOR_SIZE)
				.setParameter("size", product.getProduct().getSize().getName())
				.setParameter(
						"color",
						product.getProduct().getProdColor().getColor()
								.getName())
				.setParameter(
						"name",
						product.getProduct().getProdColor().getProduct()
								.getName()).getResultList();
		if (products == null || products.size() == 0)
			throw new ProductColorSizeDoesNotExistException();

		ReturnedProducts pr = null;
		ReturnedOrders ord = null;
		if (list.size() == 0) {
			Command com = em.find(Command.class, product.getOrder()
					.getCommand().getId());
			if (com == null)
				throw new CommandDoesNotExistException();
			ord = new ReturnedOrders(com, product.getOrder().getReturnDate());
			pr = new ReturnedProducts(ord, products.get(0));
		    em.persist(ord);
		}

		else
			pr = new ReturnedProducts(list.get(0), products.get(0));

//		try {
//			userTransaction.begin();
//			try {
			
				em.persist(pr);
//			} catch (Exception exc) {
//				System.out.println("rollback");
//				userTransaction.rollback();
//			}
//			userTransaction.commit();
//		} catch (Exception e) {
//			throw new EJBException(e);
//		}
	}

	@SuppressWarnings("unchecked")
	public boolean willBeReturned(ReturnedOrdersDTO order) {

		List<ReturnedOrders> list = em
				.createNamedQuery(ReturnedOrders.getReturnedOrder)
				.setParameter("id", order.getCommand().getId()).getResultList();
		System.out.println(list.size());
		if (list != null && list.size() == 1)
			return true;
		return false;
	}
}
