package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.*;

//import java.util.List;

/**
 * The persistent class for the Country database table.
 * 
 */
@Entity
@NamedQuery(name = Country.findAll, query = "SELECT c FROM Country c")
public class Country implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String findAll = "findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public Country() {
	}

	public Long getIdCountry() {
		return this.id;
	}

	public void setIdCountry(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [idCountry=" + id + ", name=" + name;
	}

}