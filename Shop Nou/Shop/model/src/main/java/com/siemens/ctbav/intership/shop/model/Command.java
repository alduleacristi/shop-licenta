package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
/**
 * The persistent class for the Command database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = Command.GET_COMMANDS, query = "SELECT c FROM Command c order by c.orderDate"),
				@NamedQuery(name = Command.GET_ORDERS_IN_PROGRESS, query = "SELECT c FROM Command c where c.command_status.status='In progress'")
})
public class Command implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_COMMANDS = "getCommands";
	public static final String GET_ORDERS_IN_PROGRESS = "getOrdersInProgress";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_status")
	private CommandStatus command_status;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "order_date")
	private Date orderDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_command")
	private List<ClientProduct> clientProducts;

	@OneToOne
	@JoinColumn(name="id_adress")
	private Adress adress;

	@OneToOne
	@JoinColumn(name="id_client")
	private Client client;

	public Command() {
	}

	public Long getIdCommand() {
		return this.id;
	}

	public void setIdCommand(Long idCommand) {
		this.id = idCommand;
	}

	public CommandStatus getCommand_status() {
		return command_status;
	}

	public void setCommand_status(CommandStatus command_status) {
		this.command_status = command_status;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate= deliveryDate;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<ClientProduct> getClientProducts() {
		return this.clientProducts;
	}
	

	public void setClientProducts(List<ClientProduct> clientProducts) {
		this.clientProducts = clientProducts;
	}

	public ClientProduct addClientProduct(ClientProduct clientProduct) {
		getClientProducts().add(clientProduct);

		return clientProduct;
	}

	public ClientProduct removeClientProduct(ClientProduct clientProduct) {
		getClientProducts().remove(clientProduct);
		return clientProduct;
	}

	public Adress getAdress() {
		return this.adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	

	
}