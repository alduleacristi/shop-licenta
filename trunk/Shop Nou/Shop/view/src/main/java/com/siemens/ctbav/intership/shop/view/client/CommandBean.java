package com.siemens.ctbav.intership.shop.view.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.siemens.ctbav.intership.shop.exception.client.ClientDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.CommandDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;
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

	private boolean existCommands;

	@PostConstruct
	private void initialize() {
		try {
			Client client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("client");
			
			if(client == null)
				throw new ClientDoesNotExistException("Client does not exist");
			
			commands = commandService.getDeliveredCommandToClient(client.getId());

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

		List<ClientProduct> cpList = command.getClientProducts();
		for (ClientProduct cp : cpList)
			totalPrice += (cp.getPrice() - cp.getPrice() * cp.getPercRedution()
					/ 100)
					* cp.getNrPieces();

		return totalPrice;
	}

	public boolean canBeReturned(Command command) {

		return true;
	}

}
