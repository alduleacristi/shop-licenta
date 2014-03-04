package com.siemens.ctbav.intership.shop.view.operator;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.exception.operator.NotEnoughProductsException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;
import com.siemens.ctbav.intership.shop.util.operator.AES;
import com.siemens.ctbav.intership.shop.util.operator.MailService;

@ManagedBean(name = "orders")
@RequestScoped
public class OrdersBean {

	private static int ordersPerOperator = 3;
	private double totalPerOrder;
	private double transport = (double) 0;
	private static final String clientHasToPayTransport = "Client will have to pay 20 RON for transport";
	private static final String clientDoesentHaveToPayTransport = "The client doesen't have to pay transport";
	private List<CommandDTO> orderList;
	@EJB
	private CommandService commService;

	private List<CommandDTO> allOrders;
	public List<CommandDTO> getOrderList() {
		return orderList;
	}

	private CommandDTO selectedOrder;

	public CommandDTO getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(CommandDTO selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public Double getTotalPerOrder() {
		return totalPerOrder;
	}

	public Double getTransport() {
		return transport;
	}

	@PostConstruct
	private void postConstruct() {
		allOrders = ConvertCommand.convertListOfOrders(commService.ordersInProgress());
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (user == null)
			return;
		orderList = ConvertCommand.convertListOfOrders(commService
				.getOrdersForOperator(user.getId(), ordersPerOperator));
		if (orderList.size() < ordersPerOperator) {
			int limit = ordersPerOperator - orderList.size();
			try {
				orderList.addAll(ConvertCommand.convertListOfOrders(commService
						.getRandomOrders(limit, user.getId())));
			} catch (Exception exc) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, exc
								.getMessage(), exc.getMessage()));
			}
		}

	}


	public void deliveredOrder(CommandDTO order) {
		System.out.println("in deliver");
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		order.setDeliveryDate(date);
		order.getStatus().setStatus("delivered");
		try {
			commService.setDeliveredCommand(order);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Order delivered", "Order delivered"));
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("orders.xhtml");
		} catch (NotEnoughProductsException e) {
			System.out.println("not enough, trimit mail");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"There aren't enough products in the database",
							"There aren't enough products in the database"));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"A mail will be sent to the client",
							"A mail will be sent to the client"));
//			System.out
//					.println("trimitem mail pentru explicare + pun pe sesiune lista de produse");
//
//			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productList",
//			 e.getProductsNotFound());

			sendExplicationMail(order);

		} catch (Throwable e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	private void sendExplicationMail(CommandDTO order) {
		System.out.println("trimit");
		// creez pagina de html pe care vreau sa o trimit clientului
		String encryptedId = AES.encrypt(order.getId().toString());
		String page = "<html><body><form action='http://localhost:8080/Shop4j/cancelOrder/"
				+ encryptedId
				+ "'><h1>Ne cerem scuze, dar acum nu avem destule produse in stoc pentru a va onora "
				+ "comanda.<br>Produsele le veti primi cu o intarziere de cel mult 5 zile.<br>De asemenea, aveti "
				+ "optiunea de a anula comanda, dand click pe butonul de mai jos.<br>"
				+ "<input name='button' type ='submit' value='Anuleaza comanda'/></h1></form></body></html>";
		String email = order.getClient().getUser().getEmail();
		System.out.println(email);
		try {
			// incerc sa trimit mail clientului
			 MailService.sendLink(email, "Delayed order", page, true);
		} catch (Exception e) {
			// daca nu se trimite, operatorul primeste mesaj
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"The mail couldn't be sent. Please try again later",
									"The mail couldn't be sent. Please try again later"));
		}
		System.out.println("am trimis");

	}

	public Double total() {
		if (selectedOrder != null) {
			List<ClientProductDTO> list = selectedOrder.getListProducts();
			for (ClientProductDTO client : list)
				totalPerOrder += client.getPrice() * client.getNrPieces();
			return totalPerOrder;
		}
		return (double) 0;
	}

	public String message() {
		if (totalPerOrder >= 100) {

			totalPerOrder += transport;
			return clientDoesentHaveToPayTransport;
		}

		transport = 20;
		totalPerOrder += transport;
		return clientHasToPayTransport;
	}

	public void changeOrder(CommandDTO order) {

		System.out.println("in change order");
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "User null",
							"User null"));
			return;
		}

		try {
			commService.assignOperatorsOrder(user.getId());
			commService.setOperatorNull(order.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("orders.xhtml");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

}
