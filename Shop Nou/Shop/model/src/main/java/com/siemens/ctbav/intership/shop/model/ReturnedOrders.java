package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.siemens.ctbav.intership.shop.dto.operator.CommandDTO;


@Entity
@NamedQueries({
	@NamedQuery(name = ReturnedOrders.getReturnedOrder, query = "SELECT c from ReturnedOrders c where c.command.id=:id"), 
	@NamedQuery(name = ReturnedOrders.getAllReturnedOrders, query ="select r from ReturnedOrders r where  r.returned= false order by r.returnDate desc")})
public class ReturnedOrders implements Serializable{

	private static final long serialVersionUID = 1L;
	public final static String getReturnedOrder="getReturnedOrders";
	public final static  String getAllReturnedOrders="getAllReturnedOrders";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_command")
	private Command command;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "idreturnedproducts")
	private List<ReturnedProducts> products;
	
	public List<ReturnedProducts> getProducts() {
		return products;
	}


	public void setProducts(List<ReturnedProducts> products) {
		this.products = products;
	}


	private Date returnDate;
	private Date addDate;
	private boolean returned;
	private boolean retreived;
	
	public ReturnedOrders() {
	
	}

	
	public ReturnedOrders( Command command, Date returnDate) {
		this.command = command;
		this.returnDate = returnDate;
		this.addDate = null;
		this.returned = false;
		this.retreived=false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public String toString() {
		return "ReturnedOrders [id=" + id + ", command=" + command
				+ ", returnDate=" + returnDate + ", addDate=" + addDate
				+ ", returned=" + returned + "]";
	}


	public boolean isRetreived() {
		return retreived;
	}


	public void setRetreived(boolean retreived) {
		this.retreived = retreived;
	}
	
	
	
}
