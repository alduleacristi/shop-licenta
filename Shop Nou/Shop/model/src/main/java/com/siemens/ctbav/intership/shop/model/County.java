package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.*;

//import java.util.List;

/**
 * The persistent class for the County database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = County.GET_COUNTY_BY_COUNTRY_ID, query = "SELECT a FROM County a where a.country.id = :idCountry"),
				@NamedQuery(name = County.GET_ALL_COUNTY , query = "select c from County c")})
public class County implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String GET_COUNTY_BY_COUNTRY_ID = "getCountyByCountryId";
	public static final String GET_ALL_COUNTY = "getAllCounty";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne
	@JoinColumn(name = "id_country")
	private Country country;

	public County() {
	}

	public Long getIdCounty() {
		return this.id;
	}

	public void setIdCounty(Long idCounty) {
		this.id = idCounty;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "County [id=" + id + ", name=" + name + ", country=" + country
				+ "]";
	}
}