package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name = CommandStatus.GET_STATUS_BY_NAME, query = "SELECT s FROM command_status s where s.status = :status")
})
@Entity(name = "command_status")
public class CommandStatus implements Serializable{
	
	public final static String GET_STATUS_BY_NAME="get status by name";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
