package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedOrdersDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ReturnedProductsDTO;
import com.siemens.ctbav.intership.shop.exception.client.ClientDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.CommandDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.CommandService;

/**
 * Is used as bean for CommndHistory page
 * 
 * @author Cristi
 * 
 */
@ManagedBean(name = "CommandBean")
@ViewScoped
@URLMapping(id = "CommandBean", pattern = "/client/user/Account/CommandHistory", viewId = "/client/user/Account/commandHistory.xhtml")
public class CommandBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 64914993432239107L;

	@EJB
	private CommandService commandService;

	private List<Command> commands;
	private List<ClientProduct> selectedProducts;
	private boolean existCommands;

	private static final long DAY_IN_MILIS = 1000 * 60 * 60 * 24;
	private static final int MAX_DAYS = 20;
	private final String message = "Your order has been saved";

	private Command selectedOrder;
	// private List<ClientProduct> selectedProducts;
	private DualListModel<ClientProduct> dualList;

	@PostConstruct
	private void initialize() {
		List<ClientProduct> list = new ArrayList<ClientProduct>();
		try {
			Client client = (Client) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("client");

			if (client == null)
				throw new ClientDoesNotExistException("Client does not exist");

			commands = commandService.getClientOrders(client.getId());

			boolean ok = false;
			for (int i = 0; i < commands.size(); i++) {
				if (commands.get(i).getClientProducts().size() == 0) {
					commands.remove(i);
					i--;
				} else {
					ok = true;
				}
			}

			if (ok)
				existCommands = true;
			else {
				existCommands = false;
				throw new CommandDoesNotExistException();
			}

			
			if (commands.size() == 0)
					throw new CommandDoesNotExistException();
			selectedOrder = commands.get(0);
			
			list = selectedOrder.getClientProducts();

		} catch (CommandDoesNotExistException e) {
			existCommands = false;
		} catch (ClientDoesNotExistException e) {
			existCommands = false;
		}
		dualList = new DualListModel<ClientProduct>(
				list,
				new ArrayList<ClientProduct>());
	}

	public DualListModel<ClientProduct> getDualList() {
		System.out.println("get dual list");
		return dualList;
	}

	public void setDualList(DualListModel<ClientProduct> dualList) {
		this.dualList = dualList;
	}

	public String getMessage() {
		return message;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public boolean getExistCommands() {
		return existCommands;
	}

	public void setExistCommands(boolean existCommands) {
		this.existCommands = existCommands;
	}

	public Double getTotalPrice(Command command) {
		Double totalPrice = 0.0d;

		if (command == null)
			return totalPrice;

		List<ClientProduct> cpList = command.getClientProducts();
		for (ClientProduct cp : cpList)
			totalPrice += (cp.getPrice() - cp.getPrice() * cp.getPercRedution()
					/ 100)
					* cp.getNrPieces();

		return totalPrice;
	}

	public void setSelectedOrder(Command c) {
		System.out.println("sel order");
		if (c == null)
			return;
		selectedOrder = c;

		if (selectedOrder.getClientProducts() == null)
			return;

		dualList.setSource(selectedOrder.getClientProducts());
	}

	public Command getSelectedOrder() {
		return selectedOrder;
	}

	public boolean isAlreadySaved(Command command) {
		Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
		CommandDTO com = ConvertCommand.convertCommand(command);
		ReturnedOrdersDTO retOrder = new ReturnedOrdersDTO();
		retOrder.setCommand(com);
		retOrder.setReturnDate(currentDate);
		return commandService.willBeReturned(retOrder);

	}

	public boolean canBeReturned(Command command) {
		Date date = command.getDeliveryDate();

		if (date == null)
			return false;

		Date now = new Date();
		int days = (int) ((now.getTime() - date.getTime()) / DAY_IN_MILIS);
		if (days > MAX_DAYS)
			return false;
		return !isAlreadySaved(command);
	}

	public void removeProducts() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.getFlash().setKeepMessages(true);
		try {
			commandService.removeProducts(dualList.getTarget(), selectedOrder);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"The products have been removed",
							"The products have been removed"));
			context.redirect("commandHistory.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"The following products couldn't be removed:  "
									+ e.getMessage(), e.getMessage()));
		}

	}

	private void removeProduct(ReturnedProductsDTO retProd) {
		ClientProduct cp = null;
		for (int i = 0; i < selectedProducts.size(); i++) {
			ProductColorSize pcs = selectedProducts.get(i).getProduct();
			if (sameProduct(retProd, pcs)) {
				System.out.println(pcs);
				cp = selectedProducts.get(i);
				break;
			}
		}

		if (cp != null)
			selectedProducts.remove(cp);

	}

	private boolean sameProduct(ReturnedProductsDTO retProd,
			ProductColorSize pcs) {

		ProductColorSizeDTO pDTO = ConvertProductColorSize.convert(pcs);

		if (pDTO.equals(retProd.getProduct()))
			return true;
		return false;
	}

	public void showProducts() {

	}

	public DualListModel<ClientProduct> getList() {
		List<ClientProduct> list;
		if (selectedOrder != null)
			list = selectedOrder.getClientProducts();
		else
			list = new ArrayList<ClientProduct>();

		if (list == null)
			list = new ArrayList<ClientProduct>();

		DualListModel<ClientProduct> listt = new DualListModel<ClientProduct>(
				list, new ArrayList<ClientProduct>());
		System.out.println(listt.getSource().size());
		return listt;

	}
}
