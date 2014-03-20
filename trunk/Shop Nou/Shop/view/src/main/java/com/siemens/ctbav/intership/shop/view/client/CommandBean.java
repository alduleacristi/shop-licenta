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
import javax.faces.context.FacesContext;

import org.apache.derby.tools.sysinfo;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertClientProduct;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertCommand;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertProductColorSize;
import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
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
import com.siemens.ctbav.intership.shop.util.Enum.CommandStatusEnum;

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

	private boolean existCommands;

	private static final long DAY_IN_MILIS = 1000 * 60 * 60 * 24;
	private static final int MAX_DAYS = 20;
	private final String message = "Your order has been saved";

	private Command selectedOrder;
	private List<ClientProduct> selectedProducts;

	@PostConstruct
	private void initialize() {
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
			else
				existCommands = false;
		} catch (CommandDoesNotExistException e) {
			existCommands = false;
		} catch (ClientDoesNotExistException e) {
			existCommands = false;
		}

		selectedOrder = commands.get(0);
		selectedProducts = new ArrayList<ClientProduct>();
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

	public List<ClientProduct> getSelectedProducts() {
		System.out.println("in get selected products "
				+ selectedProducts.size());
		return selectedProducts;
	}

	public void setSelectedProducts(List<ClientProduct> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public Double getTotalPrice(Command command) {
		Double totalPrice = 0.0d;

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

		selectedProducts.clear();
		selectedProducts.addAll(selectedOrder.getClientProducts());
		// for (ClientProduct cp : selectedOrder.getClientProducts()) {
		// ClientProductDTO scp = ConvertClientProduct.convert(cp);
		// selectedProducts.add(scp);
		// }
	}

	public Command getSelectedOrder() {
		return selectedOrder;
	}

	public boolean isAlreadySaved(Command command) {
		Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
		CommandDTO com = ConvertCommand.convertCommand(command);
		ReturnedOrdersDTO retOrder = new ReturnedOrdersDTO(com, currentDate, new ArrayList<ReturnedProductsDTO>());
		return commandService.willBeReturned(retOrder);

	}

	public boolean canBeReturned(Command command) {
		Date date = command.getDeliveryDate();
		Date now = new Date();
		int days = (int) ((now.getTime() - date.getTime()) / DAY_IN_MILIS);
		if (days > MAX_DAYS)
			return false;
		return !isAlreadySaved(command);
	}

	public void returnProduct(ClientProduct product) {

		Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
		CommandDTO com = ConvertCommand.convertCommand(selectedOrder);
		ReturnedOrdersDTO retOrder = new ReturnedOrdersDTO(com, currentDate, new ArrayList<ReturnedProductsDTO>());
		ProductColorSizeDTO pcs = ConvertProductColorSize.convert(product
				.getProduct());
		ReturnedProductsDTO retProd = new ReturnedProductsDTO(retOrder, pcs);

		try {
			commandService.addProductToReturn(retProd);
			System.out.println("remove product " + selectedProducts.size());
			removeProduct(retProd);
			System.out.println("s-a sters");
			System.out.println("after remove product "
					+ selectedProducts.size());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"The product has been added in the database", ""));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
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
		
		if(cp != null) selectedProducts.remove(cp);

	}

	private boolean sameProduct(ReturnedProductsDTO retProd,
			ProductColorSize pcs) {
		
		ProductColorSizeDTO pDTO = ConvertProductColorSize.convert(pcs);
		
		if(pDTO.equals(retProd.getProduct())) return true;
		return false;
	}

}