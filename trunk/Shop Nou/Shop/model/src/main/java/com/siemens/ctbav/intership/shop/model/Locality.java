package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the Locality database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Locality.FIND_BY_COUNTY_ID, query = "SELECT l FROM Locality l where l.county.id = :idCounty"),
		@NamedQuery(name = Locality.GET_ALL_LOCALITY, query = "select l from Locality l") })
public class Locality implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_COUNTY_ID = "getLocalityFromCounty";
	public static final String GET_ALL_LOCALITY = "getAllLocality";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne
	@JoinColumn(name = "id_county")
	private County county;

	public Locality() {
	}

	public Long getIdLocality() {
		return this.id;
	}

	public void setIdLocality(Long idLocality) {
		this.id = idLocality;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Override
	public String toString() {
		return "Locality [id=" + id + ", name=" + name + ", county=" + county
				+ "]";
	}

}