package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertClientProduct;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.operator.NotEnoughProductsException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.CommandService;
import com.siemens.ctbav.intership.shop.util.operator.AES;

@ManagedBean(name = "orders")
@RequestScoped
public class OrdersBean {

	private static int ordersPerOperator = 3;
	private boolean haveToAddProducts = false;
	private double totalPerOrder;
	private double transport = (double) 0;
	private static final String clientHasToPayTransport = "Client will have to pay 20 RON for transport";
	private static final String clientDoesentHaveToPayTransport = "The client doesen't have to pay transport";
	private List<CommandDTO> orderList;
	@EJB
	private CommandService commService;

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

	

	private List<ClientProductDTO> stoppedOrders;

	public Double getTotalPerOrder() {
		return totalPerOrder;
	}

	public Double getTransport() {
		return transport;
	}

	@PostConstruct
	private void postConstruct() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if(user == null) return;
		orderList = ConvertCommand.convertListOfOrders(commService
				.getOrdersForOperator(user.getId(), ordersPerOperator));
		//System.out.println(orderList.size());
		if (orderList.size() < ordersPerOperator) {
			int limit = ordersPerOperator - orderList.size();
			try {
				orderList.addAll(ConvertCommand.convertListOfOrders(commService
						.getRandomOrders(limit, user.getId())));
			} catch (Exception exc) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, exc.getMessage(), exc
								.getMessage()));
			}
		}
		stoppedOrders =ConvertClientProduct.convertList(commService.getStoppedOrders(user.getId()));
		if (stoppedOrders.size() > 0)
			haveToAddProducts = true;

	}

	public void deliveredOrder(CommandDTO order) {
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		order.setDeliveryDate(date);
		order.getStatus().setStatus("delivered");
		try {
			commService.setDeliveredCommand(order);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Order delivered",
							"Order delivered"));
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("orders.xhtml");
		} catch (NotEnoughProductsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"There aren't enough products in the database",
							"There aren't enough products in the database"));
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,"A mail will be sent to the client",
							"A mail will be sent to the client"));
		//	System.out.println("trimitem mail pentru explicare + pun pe sesiune lista de produse");
			
			//cand operatorul vrea sa adauge produse, pe pagina de adaugare produsele din lista productList 
			//o sa apara primele, apoi cele din list stoppedOrders
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productList", e.getProductsNotFound());
			
			sendExplicationMail(order);

		} catch (Throwable e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e
							.getMessage()));
		}

	}

	private void sendExplicationMail(CommandDTO order) {
		// creez pagina de html pe care vreau sa o trimit clientului
		String encryptedId = AES.encrypt(order.getId().toString());
		String page = "<html><body><form action='http://localhost:8080/Shop4j/cancelOrder/"
				+ encryptedId
				+ "'><h1>Ne cerem scuze, dar acum nu avem destule produse in stoc pentru a va onora "
				+ "comanda.<br>Produsele le veti primi cu o intarziere de cel mult 5 zile.<br>De asemenea, aveti "
				+ "optiunea de a anula comanda, dand click pe butonul de mai jos.<br>"
				+ "<input name='button' type ='submit' value='Anuleaza comanda'/></h1></form></body></html>";
		String email = order.getClient().getUser().getEmail();
		try {
			// incerc sa trimit mail clientului
			// MailService.sendLink(email, "Delayed order", page, true);
		} catch (Exception e) {
			// daca nu se trimite, operatorul primeste mesaj
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"The mail couldn't be sent. Please try again later",
									"The mail couldn't be sent. Please try again later"));
		}

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
		if (totalPerOrder > 100) {

			totalPerOrder += transport;
			return clientDoesentHaveToPayTransport;
		}
		transport = 20;
		totalPerOrder += transport;
		return clientHasToPayTransport;
	}

	public boolean isHaveToAddProducts() {
	//	System.out.println(haveToAddProducts);
		return haveToAddProducts;
	}

	public void setHaveToAddProducts(boolean haveToAddProducts) {
		this.haveToAddProducts = haveToAddProducts;
	}

	public void addProducts() {
		try {
			//pun in map comenzile pt care nu exista destule produse si trebuie adaugat
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("stoppedOrders", stoppedOrders);
			FacesContext.getCurrentInstance().getExternalContext().redirect("addMissingProducts.xhtml");
		} catch (IOException e) {
			
		}

	}

	public void changeOrder(CommandDTO order) {
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
			FacesContext.getCurrentInstance().getExternalContext().redirect("orders.xhtml");
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e
							.getMessage()));
		}
	}

  
}

