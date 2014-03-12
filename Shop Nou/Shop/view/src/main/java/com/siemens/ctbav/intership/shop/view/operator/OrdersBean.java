package com.siemens.ctbav.intership.shop.view.operator;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
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
import com.siemens.ctbav.intership.shop.exception.operator.CommandNotFoundException;
import com.siemens.ctbav.intership.shop.exception.operator.IncorrectInputException;
import com.siemens.ctbav.intership.shop.exception.operator.NotEnoughProductsException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.report.operator.GenerateCSV;
import com.siemens.ctbav.intership.shop.report.operator.GenerateJson;
import com.siemens.ctbav.intership.shop.report.operator.GeneratePDF;
import com.siemens.ctbav.intership.shop.report.operator.GenerateReport;
import com.siemens.ctbav.intership.shop.report.operator.GenerateXML;
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
	private String[] reports;
	@EJB
	private CommandService commService;

	private Date from, to;
	private Double minValue, maxValue;

	private GenerateReport generate;
	public String getFilename() {
		java.sql.Date currDate = new java.sql.Date(Calendar.getInstance()
				.getTimeInMillis());
		return "d:\\Reports\\orders" + currDate;
	}

	// private List<CommandDTO> allOrders;
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

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public String[] getReports() {
		return reports;
	}

	public void setReports(String[] reports) {
		this.reports = reports;
	}
	@PostConstruct
	private void postConstruct() {
		// setAllOrders(ConvertCommand.convertListOfOrders(commService.ordersInProgress()));
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
			// System.out
			// .println("trimitem mail pentru explicare + pun pe sesiune lista de produse");
			//
			// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productList",
			// e.getProductsNotFound());

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

	public void results() {
		List<CommandDTO> orders;
		try {
			testAnythingSelected();
			testCorrectDates();
			testTotalLimits();
			if (minValue == null && maxValue == null && minValue != 0.0
					&& maxValue != 0.0) {
				// inseamna ca il intereseza doar data
				orders = ConvertCommand.convertListOfOrders(commService
						.getOrdersBetweenDates(from, to));
				orderList = orders;
				return;
			}

			// inseamna ca il intereseaza doar totalul
			if (from == null && to == null) {
				orders = ConvertCommand.convertListOfOrders(commService
						.getOrdersBetweenTotal(minValue, maxValue));
				orderList = orders;
				return;
			}

			// se face filtrare dupa ambele campuri

			orders = ConvertCommand
					.convertListOfOrders(commService
							.getOrdersBetweenDateAndTotals(from, to, minValue,
									maxValue));

		} catch (IncorrectInputException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		} catch (CommandNotFoundException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	private void testTotalLimits() throws IncorrectInputException {
		if ((minValue != null && maxValue == null)
				|| (minValue == null && maxValue != null)) {
			minValue = maxValue = null;
			throw new IncorrectInputException("Please insert both limits");

		}

		if (minValue != maxValue && maxValue != 0.0 && minValue >= maxValue) {
			throw new IncorrectInputException(
					"Max value must be greater than min value");
		}
	}

	private void testCorrectDates() throws IncorrectInputException {

		if ((from != null && to == null) || (from == null && to != null)) {
			to = from = null;
			throw new IncorrectInputException("Please insert both dates");
		}

		if (from != null && to != null && to.compareTo(from) < 0) {
			to = from = null;
			throw new IncorrectInputException("To date must be after from date");

		}
	}

	private void testAnythingSelected() throws IncorrectInputException {
		if (to == null && from == null && minValue == null) {
			throw new IncorrectInputException("Please insert a filter");

		}

	}

	private boolean contains(int val) {
		for (int i = 0; i < reports.length; i++) {
			int value = Integer.parseInt(reports[i]);
			if (value == val)
				return true;
		}
		return false;
	}

	public void generateReports() {
	//	for (int i = 0; i < reports.length; i++)
		//	System.out.println(reports[i]);
		try {
			if (contains(Reports.PDF.getValue())) {
				generate = new GeneratePDF(orderList, new FileWriter(
						getFilename() + ".pdf"));
				generate.generate();
			}

			if (contains(Reports.XML.getValue())) {

				generate = new GenerateXML(orderList, new FileWriter(
						getFilename() + ".xml"));
			//	System.out.println(getFilename() + ".xml");
				generate.generate();
			}
			if (contains(Reports.JSON.getValue())) {
				generate = new GenerateJson(orderList, new FileWriter(
						getFilename() + ".json"));
				generate.generate();
			}
			if (contains(Reports.CSV.getValue())) {
				generate = new GenerateCSV(orderList, new FileWriter(
						getFilename() + ".csv"));
				generate.generate();
			}
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"The reports have been generated in folder Reports",
									""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	

}
