package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.*;

//import java.util.List;

/**
 * The persistent class for the Adress database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Adress.GET_ADRESS_BY_ID, query = "SELECT a FROM Adress a where a.id = :idAdress"),
		@NamedQuery(name = Adress.GET_ALL_ADRESS, query = "select a from Adress a"),
		@NamedQuery(name = Adress.GET_CLIENT_ADRESS, query = "select a from Adress a where a.client.id = :idClient") })
public class Adress implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_ADRESS_BY_ID = "getAdressByID";
	public static final String GET_ALL_ADRESS = "getAllAdress";
	public static final String GET_CLIENT_ADRESS = "getClientAdress";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long block;

	private Long flat;

	private Long number;

	private String staircase;

	private String street;

	@OneToOne
	@JoinColumn(name = "id_locality")
	private Locality locality;

	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;

	public void setNumber(Long number) {
		this.number = number;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public Adress() {
	}

	public Long getIdAdress() {
		return this.id;
	}

	public void setIdAdress(Long idAdress) {
		this.id = idAdress;
	}

	public Long getBlock() {
		return this.block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getFlat() {
		return this.flat;
	}

	public void setFlat(Long flat) {
		this.flat = flat;
	}

	public Long getNumber() {
		return this.number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getStaircase() {
		return this.staircase;
	}

	public void setStaircase(String staircase) {
		this.staircase = staircase;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Street ").append(this.street).append(",")
				.append(this.number);

		if (this.block != null)
			if (!this.block.equals(""))
				sb.append(", block ").append(this.block);

		if (this.staircase != null)
			if (!this.staircase.equals(""))
				sb.append(", staircase ").append(this.staircase);

		if (this.flat != null)
			if (!this.flat.equals(""))
				sb.append(", flat ").append(this.flat);

		sb.append(",").append(this.locality.getName()).append(",")
				.append(this.locality.getCounty().getName()).append(",")
				.append(this.locality.getCounty().getCountry().getName());

		return sb.toString();
	}
}