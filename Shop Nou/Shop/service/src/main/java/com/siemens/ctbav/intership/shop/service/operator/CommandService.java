package com.siemens.ctbav.intership.shop.service.operator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.exception.client.ProductDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.CommandStatusException;
import com.siemens.ctbav.intership.shop.exception.operator.NotEnoughProductsException;
import com.siemens.ctbav.intership.shop.exception.operator.UserNotFoundException;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;
import com.siemens.ctbav.intership.shop.model.CommandStatus;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.model.ReturnedOrders;
import com.siemens.ctbav.intership.shop.model.ReturnedProducts;
import com.siemens.ctbav.intership.shop.model.User;

@Stateless
public class CommandService {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Command> ordersInProgress() {
		List<Command> list = em
				.createNamedQuery(Command.GET_ORDERS_IN_PROGRESS)
				.getResultList();
		// for (Command com : list)
		// for (ClientProduct client : com.getClientProducts())
		// System.out.println(client.getProduct().getProductcolor()
		// .getProduct().getName());
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<ReturnedOrders> getReturnedOrders()
			throws CommandNotFoundException {
		System.out.println("iau comenzule returnate");
		List<ReturnedOrders> list = em.createNamedQuery(
				ReturnedOrders.getAllReturnedOrders).getResultList();
		if (list == null || list.size() == 0)
			throw new CommandNotFoundException("There are no returned orders");
		System.out.println("size : " + list.size());
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setDeliveredCommand(CommandDTO command)
			throws CommandNotFoundException, CommandStatusException,
			NotEnoughProductsException {
		Command cmd = em.find(Command.class, command.getId());
		if (cmd == null)
			throw new CommandNotFoundException(
					"No command was found with this id!");
		List<CommandStatus> list = em
				.createNamedQuery(CommandStatus.GET_STATUS_BY_NAME)
				.setParameter("status", command.getStatus().getStatus())
				.getResultList();
		if (list.size() == 0)
			throw new CommandStatusException("No status found with this name");
		if (list.size() > 1)
			throw new CommandStatusException("More than one status was found");

		List<ClientProduct> listProducts = cmd.getClientProducts();
		NotEnoughProductsException exc = null;
		for (ClientProduct prod : listProducts) {
			long nrPieces = prod.getProduct().getNrOfPieces()
					- prod.getNrPieces();
			if (nrPieces < 0) {
				exc = new NotEnoughProductsException(prod);
			}
		}
		if (exc != null)
			throw exc;

		for (ClientProduct prod : listProducts) {
			long nrPieces = prod.getProduct().getNrOfPieces()
					- prod.getNrPieces();
			prod.getProduct().setNrOfPieces(nrPieces);
			em.merge(prod); // aici salvez in baza schimbarile
		}

		cmd.setDeliveryDate(command.getDeliveryDate());
		cmd.setCommand_status(list.get(0));
		em.merge(cmd);

	}

	public Command getOrderById(Long id) {
		return em.find(Command.class, id);

	}

	public void cancelOrder(Long id) throws CommandNotFoundException {
		Command cmd = getOrderById(id);
		if (cmd != null)
		em.remove(cmd);
	}

	@SuppressWarnings("unchecked")
	public List<ClientProduct> getStoppedOrders(Long id) {
		List<Command> list = em
				.createNamedQuery(Command.GET_ORDERS_FOR_OPERATOR)
				.setParameter("id", id).getResultList();
		List<ClientProduct> stoppedOrders = new ArrayList<ClientProduct>();
		for (Command c : list) {
			for (ClientProduct cp : c.getClientProducts()) {
				if (cp.getProduct().getNrOfPieces() - cp.getNrPieces() < 0)
					stoppedOrders.add(cp);
			}
		}
		// if(stoppedOrders.size() == 0) System.out.println("niciun element");
		// else System.out.println("nu e goala");
		return stoppedOrders;
	}

	@SuppressWarnings("unchecked")
	public List<Command> getOrdersForOperator(Long id_operator, int nrOrders) {
		List<Command> list = em
				.createNamedQuery(Command.GET_ORDERS_FOR_OPERATOR)
				.setParameter("id", id_operator).setMaxResults(nrOrders)
				.getResultList();
		 if(list.size() > 0) System.out.println(list.get(0).getId()); else
		 System.out.println("lista vida");
		return list;
	}

	private void setIdOperator(List<Command> list, Long idOp)
			throws CommandNotFoundException, UserNotFoundException {
		for (Command comm : list) {
			Command command = em.find(Command.class, comm.getId());
			if (command == null)
				throw new CommandNotFoundException();
			User user = em.find(User.class, idOp);
			if (user == null)
				throw new UserNotFoundException();
			command.setUser(user);
			em.merge(command);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Command> getRandomOrders(int limit, Long idOperator)
			throws CommandNotFoundException, UserNotFoundException {
		List<Command> list = em.createNamedQuery(Command.GET_RANDOM_ORDERS)
				.setMaxResults(limit).getResultList();
		setIdOperator(list, idOperator);
		return list;
	}

//	@SuppressWarnings("unchecked")
//	public void assignOperatorsOrder(long id_operator)
//			throws CommandNotFoundException, UserNotFoundException {
//		List<Command> comm = em.createNamedQuery(Command.GET_ORDER)
//				.setMaxResults(1).getResultList();
//		if (comm == null || comm.size() == 0)
//			throw new CommandNotFoundException("No more unassigned orders");
//
//		User user = em.find(User.class, id_operator);
//		if (user == null)
//			throw new UserNotFoundException("No operator with this id: "
//					+ id_operator);
//		comm.get(0).setUser(user);
//	}

	public void setOperatorNull(Long idOrder) throws CommandNotFoundException {
		Command comm = em.find(Command.class, idOrder);
		if (comm == null)
			throw new CommandNotFoundException();
		comm.setUser(null);
		em.merge(comm);
	}

	@SuppressWarnings("unchecked")
	public List<Command> getOrdersForClient(Long id) {
		List<Command> list = em.createNamedQuery(Command.GET_CLIENTS_ORDERS)
				.setParameter("id", id).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Command> getOrdersBetweenDates(Date from, Date to)
			throws CommandNotFoundException {
		List<Command> orders = em
				.createNamedQuery(Command.GET_ORDERS_BETWEEN_DATES)
				.setParameter("date1", new java.sql.Date(from.getTime()))
				.setParameter("date2", new java.sql.Date(to.getTime()))
				.getResultList();
		if (orders == null || orders.size() == 0) {
			throw new CommandNotFoundException(
					"There are no orders between this dates");
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<Command> getOrdersBetweenTotal(Double total1, Double total2)
			throws CommandNotFoundException {
		List<Command> list = em
				.createNamedQuery(Command.GET_ORDERS_IN_PROGRESS)
				.getResultList();
		if (list == null || list.size() == 0)
			throw new CommandNotFoundException(
					"There aren't orders in progress");
		List<Command> filteredList = new ArrayList<Command>();

		for (Command c : list) {
			Double total = getTotalForOrder(c);
			if (total >= total1 && total <= total2)
				filteredList.add(c);
		}

		return filteredList;
	}

	private Double getTotalForOrder(Command c) {
		Double total = (double) 0;
		for (ClientProduct cp : c.getClientProducts())
			total += cp.getPrice();

		return total;
	}

	public List<Command> getOrdersBetweenDateAndTotals(Date from, Date to,
			Double minValue, double maxValue) throws CommandNotFoundException {
		List<Command> orders = getOrdersBetweenDates(from, to);
		for (int i = 0; i < orders.size(); i++) {
			Double total = getTotalForOrder(orders.get(i));
			if (total < minValue || total > maxValue)
				orders.remove(i);
		}

		return orders;
	}

	@SuppressWarnings("unchecked")
	public List<ReturnedProducts> getReturnedProductsForOrder(
			ReturnedOrdersDTO order) {
		System.out.println(order);
		List<ReturnedProducts> list = (List<ReturnedProducts>) em
				.createNamedQuery(ReturnedProducts.getReturnedProductsForOrder)
				.setParameter("id", order.getId()).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public void addReturnedProductsPieces(ClientProductDTO cp) throws ProductDoesNotExistException {
		Long nrPieces = cp.getNrPieces();
		System.out.println("nr bucati de adaugat " + cp.getNrPieces());
		ProductColorSizeDTO pcs = cp.getProduct();
		List<ProductColorSize> products = em
				.createNamedQuery(ProductColorSize.GET_PRODUCT_COLOR_SIZE)
				.setParameter("size", pcs.getSize().getName())
				.setParameter("color", pcs.getProdColor().getColor().getName())
				.setParameter("name", pcs.getProdColor().getProduct().getName())
				.getResultList();
		
		if (products == null || products.size() ==0) throw new ProductDoesNotExistException();
		
		ProductColorSize product = products.get(0);
		System.out.println(product.getNrOfPieces());
		product.setNrOfPieces(nrPieces + product.getNrOfPieces());
		System.out.println(product.getNrOfPieces());
		em.merge(product);

	}


	public void setAsReturned(ReturnedOrdersDTO order) {
		Date currDate = Calendar.getInstance().getTime();
		ReturnedOrders ord = em.find(ReturnedOrders.class, order.getId());
		ord.setAddDate(currDate);
		ord.setReturned(true);
		em.merge(ord);
	}

	public void setAsRetreived(ReturnedOrdersDTO order) throws CommandNotFoundException {
		ReturnedOrders ord = em.find(ReturnedOrders.class, order.getId());
		if(ord == null) throw new CommandNotFoundException("No returned order with this id");
		ord.setRetreived(true);
		em.merge(ord);
		
	}

	public List<ReturnedProducts> getProductsReturned(Long idOrder) {
		
		List<ReturnedProducts> list = new ArrayList<ReturnedProducts>();
		list= em.createNamedQuery(ReturnedProducts.getReturnedProductsForOrder).getResultList();
		System.out.println("rezultate: "+ list.size());
		return list;
	}

}
