package com.siemens.ctbav.intership.shop.service.operator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.CommandStatusException;
import com.siemens.ctbav.intership.shop.exception.operator.NotEnoughProductsException;
import com.siemens.ctbav.intership.shop.exception.operator.UserNotFoundException;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;
import com.siemens.ctbav.intership.shop.model.CommandStatus;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
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
		for (Command com : list)
			for (ClientProduct client : com.getClientProducts())
				System.out.println(client.getProduct().getProductcolor()
						.getProduct().getName());
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
		if (cmd == null)
			throw new CommandNotFoundException(
					"This order has already been canceled!!!");
		em.remove(cmd);
	}

	@SuppressWarnings("unchecked")
	public List<ClientProduct> getStoppedOrders(Long id) {
		List<Command> list = em.createNamedQuery(Command.GET_ORDERS_FOR_OPERATOR).setParameter("id", id)
				.getResultList();
		List<ClientProduct> stoppedOrders = new ArrayList<ClientProduct>();
		for (Command c : list) {
			for (ClientProduct cp : c.getClientProducts()) {
				if (cp.getProduct().getNrOfPieces() - cp.getNrPieces() < 0)
					stoppedOrders.add(cp);
			}
		}
		//if(stoppedOrders.size() == 0) System.out.println("niciun element"); else System.out.println("nu e goala");
		return stoppedOrders;
	}

	@SuppressWarnings("unchecked")
	public List<Command> getOrdersForOperator(Long id_operator, int nrOrders) {
		List<Command> list= em.createNamedQuery(Command.GET_ORDERS_FOR_OPERATOR)
				.setParameter("id", id_operator).setMaxResults(nrOrders)
				.getResultList();
	//	if(list.size() > 0) System.out.println(list.get(0).getId()); else System.out.println("lista vida");
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

	
	@SuppressWarnings("unchecked")
	public void assignOperatorsOrder(long id_operator) throws CommandNotFoundException, UserNotFoundException{
	//	System.out.println(id_operator);
		List<Command> commList= em.createNamedQuery(Command.GET_ORDER).getResultList();
		if(commList.size() ==0)
			throw new CommandNotFoundException("No more unassigned orders");
		
		Command comm = commList.get(0);
		User user = em.find(User.class, id_operator);
		if(user == null) throw new UserNotFoundException("No operator with this id: "+ id_operator);
		comm.setUser(user);	
	}
	
	public void setOperatorNull(Long idOrder) throws CommandNotFoundException{
		Command comm = em.find(Command.class, idOrder);
		if(comm == null) throw new CommandNotFoundException();
		comm.setUser(null);
		em.merge(comm);
	}
	
	@SuppressWarnings("unchecked")
	public List<Command> getOrdersForClient(Long id){
		List<Command> list = em.createNamedQuery(Command.GET_CLIENTS_ORDERS).setParameter("id", id).getResultList();
		return list;
	}

}






